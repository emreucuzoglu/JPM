package com.jpm.tech.tests.service;

import com.jpm.tech.entity.DailyRanking;
import com.jpm.tech.entity.DailyReport;
import com.jpm.tech.entity.Instruction;
import com.jpm.tech.service.ProcessorService;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.jpm.tech.common.enums.EOperation.BUY;
import static com.jpm.tech.common.enums.EOperation.SELL;
import static com.jpm.tech.service.ProcessorService.getDaily;
import static com.jpm.tech.service.ProcessorService.getRanking;
import static com.jpm.tech.util.RandomDataGenerator.generateRandomInstruction;
import static com.jpm.tech.util.RandomDataGenerator.generateRandomName;
import static java.time.LocalDate.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProcessorServiceTest {
    @Test
    public void processSettlementDate_shouldAddOneDay() throws Exception {
        Instruction instruction = generateRandomInstruction(generateRandomName());
        instruction.setCurrency("EUR");
        instruction.setSettlementDate(of(2017, 5, 14));

        ProcessorService.processSettlementDate(instruction);

        assertEquals(of(2017, 5, 15), instruction.getSettlementDate());
    }

    @Test
    public void processSettlementDate_shouldAddTwoDays() throws Exception {
        Instruction instruction = generateRandomInstruction(generateRandomName());
        instruction.setCurrency("EUR");
        instruction.setSettlementDate(of(2017, 5, 13));

        ProcessorService.processSettlementDate(instruction);

        assertEquals(of(2017, 5, 15), instruction.getSettlementDate());
    }

    @Test
    public void processSettlementDate_shouldKeepDate() throws Exception {
        Instruction instruction = generateRandomInstruction(generateRandomName());
        instruction.setCurrency("EUR");
        instruction.setSettlementDate(of(2017, 5, 15));

        ProcessorService.processSettlementDate(instruction);

        assertEquals(of(2017, 5, 15), instruction.getSettlementDate());
    }

    @Test
    public void getDaily_happyPath() throws Exception {
        List<Instruction> instructions = generateData();
        List<DailyReport> actualList = getDaily(instructions);
        LocalDate localDate = of(2017, 5, 15);

        assertNotNull(actualList);
        assertEquals(2, actualList.size());

        DailyReport tmpDailyReport = actualList.get(0);
        assertNotNull(tmpDailyReport);
        assertEquals(localDate, tmpDailyReport.getSettlementDate());
        assertEquals(204.45000000000002, tmpDailyReport.getTotalBuy(), 0.0);
        assertEquals(244.20000000000005, tmpDailyReport.getTotalSell(), 0.0);

        tmpDailyReport = actualList.get(1);
        assertNotNull(tmpDailyReport);
        assertEquals(localDate.plusDays(2), tmpDailyReport.getSettlementDate());
        assertEquals(0.0, tmpDailyReport.getTotalBuy(), 0.0);
        assertEquals(105.45, tmpDailyReport.getTotalSell(), 0.0);
    }

    @Test
    public void getRanking_happyPathForBuy() throws Exception {
        List<Instruction> instructions = generateData();
        List<DailyRanking> actualList = getRanking(BUY, instructions);
        LocalDate localDate = of(2017, 5, 15);

        assertNotNull(actualList);
        assertEquals(2, actualList.size());

        DailyRanking tmpDailyRanking = actualList.get(0);
        assertNotNull(tmpDailyRanking);
        assertEquals(localDate, tmpDailyRanking.getSettlementDate());
        assertEquals("test3", tmpDailyRanking.getEntityName());
        assertEquals(1, tmpDailyRanking.getRank());
        assertEquals(105.45, tmpDailyRanking.getAmount(), 0.0);

        tmpDailyRanking = actualList.get(1);
        assertNotNull(tmpDailyRanking);
        assertEquals(localDate, tmpDailyRanking.getSettlementDate());
        assertEquals("test2", tmpDailyRanking.getEntityName());
        assertEquals(2, tmpDailyRanking.getRank());
        assertEquals(99.00000000000001, tmpDailyRanking.getAmount(), 0.0);

    }

    @Test
    public void getRanking_happyPathForSell() throws Exception {
        List<Instruction> instructions = generateData();
        List<DailyRanking> actualList = getRanking(SELL, instructions);
        LocalDate localDate = of(2017, 5, 15);

        assertNotNull(actualList);
        assertEquals(3, actualList.size());

        DailyRanking tmpDailyRanking = actualList.get(0);
        assertNotNull(tmpDailyRanking);
        assertEquals(localDate, tmpDailyRanking.getSettlementDate());
        assertEquals("test2", tmpDailyRanking.getEntityName());
        assertEquals(1, tmpDailyRanking.getRank());
        assertEquals(122.10000000000002, tmpDailyRanking.getAmount(), 0.0);

        tmpDailyRanking = actualList.get(1);
        assertNotNull(tmpDailyRanking);
        assertEquals(localDate, tmpDailyRanking.getSettlementDate());
        assertEquals("test1", tmpDailyRanking.getEntityName());
        assertEquals(2, tmpDailyRanking.getRank());
        assertEquals(122.10000000000002, tmpDailyRanking.getAmount(), 0.0);

        tmpDailyRanking = actualList.get(2);
        assertNotNull(tmpDailyRanking);
        assertEquals(localDate.plusDays(2), tmpDailyRanking.getSettlementDate());
        assertEquals("test4", tmpDailyRanking.getEntityName());
        assertEquals(1, tmpDailyRanking.getRank());
        assertEquals(105.45, tmpDailyRanking.getAmount(), 0.0);

    }

    private List<Instruction> generateData() {
        List<Instruction> instructions = new ArrayList<>();
        LocalDate localDate = of(2017, 5, 15);
        instructions.add(Instruction.builder()
                .name("test1")
                .operation(SELL)
                .agreedFx(1.11)
                .currency("EUR")
                .instructionDate(localDate)
                .settlementDate(localDate)
                .units(100)
                .unitPrice(1.1)
                .build());

        instructions.add(Instruction.builder()
                .name("test2")
                .operation(SELL)
                .agreedFx(1.11)
                .currency("EUR")
                .instructionDate(localDate)
                .settlementDate(localDate)
                .units(100)
                .unitPrice(1.1)
                .build());

        instructions.add(Instruction.builder()
                .name("test2")
                .operation(BUY)
                .agreedFx(0.90)
                .currency("GBP")
                .instructionDate(localDate)
                .settlementDate(localDate)
                .units(100)
                .unitPrice(1.1)
                .build());

        instructions.add(Instruction.builder()
                .name("test3")
                .operation(BUY)
                .agreedFx(0.95)
                .currency("GBP")
                .instructionDate(localDate)
                .settlementDate(localDate)
                .units(100)
                .unitPrice(1.11)
                .build());

        instructions.add(Instruction.builder()
                .name("test4")
                .operation(SELL)
                .agreedFx(0.95)
                .currency("TRL")
                .instructionDate(localDate.plusDays(1))
                .settlementDate(localDate.plusDays(2))
                .units(100)
                .unitPrice(1.11)
                .build());

        return instructions;
    }

}