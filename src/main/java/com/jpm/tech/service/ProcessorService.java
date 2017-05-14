package com.jpm.tech.service;


import com.jpm.tech.common.enums.EOperation;
import com.jpm.tech.entity.DailyRanking;
import com.jpm.tech.entity.DailyReport;
import com.jpm.tech.entity.Instruction;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jpm.tech.common.enums.EOperation.BUY;
import static com.jpm.tech.common.enums.EOperation.SELL;
import static com.jpm.tech.util.Util.dateOffsetToWeekday;
import static com.jpm.tech.util.Util.isSundayCurrency;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

public class ProcessorService {

    public static Instruction processSettlementDate(Instruction instruction) {
        Instruction result = instruction;
        if (result != null && result.getSettlementDate() != null && result.getInstructionDate() != null) {
            LocalDate settlementDate = result.getSettlementDate();
            if (settlementDate.isBefore(result.getInstructionDate())) {
                settlementDate = result.getInstructionDate();
            }
            DayOfWeek endDay = FRIDAY;
            if (isSundayCurrency(instruction.getCurrency())) {
                endDay = THURSDAY;
            }
            result.setSettlementDate(settlementDate.plusDays(dateOffsetToWeekday(settlementDate, endDay)));
        }

        return result;
    }

    public static List<DailyReport> getDaily(List<Instruction> instructions) {
        List<DailyReport> resultList = new ArrayList<>();
        instructions.stream()
                .collect(groupingBy(Instruction::getSettlementDate,
                        groupingBy(Instruction::getOperation, summingDouble(Instruction::getPriceInUsd))))
                .entrySet().stream()
                .sorted(comparing(Map.Entry::getKey))
                .forEachOrdered(x -> {
                    Map<EOperation, Double> tmpValue = x.getValue();
                    double buyValue = tmpValue.get(BUY) != null ? tmpValue.get(BUY) : 0;
                    double sellValue = tmpValue.get(SELL) != null ? tmpValue.get(SELL) : 0;
                    resultList.add(DailyReport.builder()
                            .settlementDate(x.getKey())
                            .totalBuy(buyValue)
                            .totalSell(sellValue)
                            .build());
                });
        return resultList;
    }

    public static List<DailyRanking> getRanking(EOperation operation, List<Instruction> instructions) {
        List<DailyRanking> resultList = new ArrayList<>();
        instructions.stream()
                .filter(instruction -> operation.equals(instruction.getOperation()))
                .collect(groupingBy(Instruction::getSettlementDate,
                        groupingBy(Instruction::getName, summingDouble(Instruction::getPriceInUsd))))
                .entrySet().stream()
                .sorted(comparing(Map.Entry::getKey))
                .forEachOrdered(x -> {
                    final AtomicInteger rank = new AtomicInteger(0);
                    x.getValue().entrySet().stream().sorted((i1, i2) -> i2.getValue().compareTo(i1.getValue()))
                            .forEachOrdered(y -> resultList.add(DailyRanking.builder()
                                    .settlementDate(x.getKey())
                                    .entityName(y.getKey())
                                    .rank(rank.incrementAndGet())
                                    .amount(y.getValue())
                                    .build()));
                });
        return resultList;
    }


}
