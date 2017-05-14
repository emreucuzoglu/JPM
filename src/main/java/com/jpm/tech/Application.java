package com.jpm.tech;

import com.jpm.tech.entity.Instruction;
import com.jpm.tech.service.ProcessorService;

import java.util.List;
import java.util.logging.Level;

import static com.jpm.tech.common.enums.EOperation.BUY;
import static com.jpm.tech.common.enums.EOperation.SELL;
import static com.jpm.tech.service.ProcessorService.getDaily;
import static com.jpm.tech.service.ProcessorService.getRanking;
import static com.jpm.tech.util.OutputUtil.*;
import static com.jpm.tech.util.RandomDataGenerator.generateData;
import static java.util.logging.Level.*;
import static java.util.stream.Collectors.toList;

public class Application {
    public static final Level LOG_LEVEL = INFO;

    public static void main(String[] args) {
        List<Instruction> instructions = generateData(10000);
        logData("Generated Input Data", FINE, FINER, instructions);

        instructions = instructions.stream().map(ProcessorService::processSettlementDate).collect(toList());
        logData("Updated Settlement Dates", FINE, FINER, instructions);

        printDaily(getDaily(instructions));
        printRanking("Ranking for outgoing everyday", getRanking(BUY, instructions));
        printRanking("Ranking for incoming everyday", getRanking(SELL, instructions));

    }


}
