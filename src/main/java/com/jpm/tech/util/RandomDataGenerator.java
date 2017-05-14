package com.jpm.tech.util;


import com.jpm.tech.common.enums.EOperation;
import com.jpm.tech.entity.Instruction;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jpm.tech.common.enums.EOperation.BUY;
import static com.jpm.tech.common.enums.EOperation.SELL;

public final class RandomDataGenerator {
    private static final String[] currencies = {"AED", "EUR", "SAR", "GBP"};
    private static final SecureRandom random = new SecureRandom();
    private static final AtomicInteger count = new AtomicInteger(0);

    private RandomDataGenerator() {
    }

    public static List<Instruction> generateData(int size) {
        List<Instruction> results = new ArrayList<>();
        String tmpName = generateRandomName();
        for (int i = 0; i < size; i++) {
            if (random.nextInt(3) < 1) {
                tmpName = generateRandomName();
            }
            results.add(generateRandomInstruction(tmpName));

        }
        return results;
    }

    public static Instruction generateRandomInstruction(String name) {
        EOperation operation = random.nextInt(2) == 0 ? BUY : SELL;
        LocalDate instructionDate = LocalDate.now().plusDays(random.nextInt(50));
        LocalDate settlementDate = instructionDate.plusDays(random.nextInt(50));

        return Instruction.builder()
                .name(name)
                .operation(operation)
                .agreedFx(random.nextFloat())
                .currency(currencies[random.nextInt(4)])
                .instructionDate(instructionDate)
                .settlementDate(settlementDate)
                .units(random.nextInt(500))
                .unitPrice(random.nextFloat())
                .build();
    }

    public static String generateRandomName() {
        return "entity" + count.getAndAdd(1);
    }
}
