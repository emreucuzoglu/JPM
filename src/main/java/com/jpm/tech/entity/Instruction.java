package com.jpm.tech.entity;

import com.jpm.tech.common.enums.EOperation;
import lombok.*;

import java.time.LocalDate;

import static com.jpm.tech.util.Util.format;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class Instruction {

    private String name;
    private EOperation operation;
    private double agreedFx;
    private String currency;
    private LocalDate instructionDate;
    private LocalDate settlementDate;
    private int units;
    private double unitPrice;

    public double getPriceInUsd() {
        return agreedFx * units * unitPrice;
    }

    @Override
    public String toString() {
        return "[" +
                "name='" + name + '\'' +
                ", operation=" + operation +
                ", agreedFx=" + agreedFx +
                ", currency='" + currency + '\'' +
                ", instructionDate=" + format(instructionDate) +
                ", settlementDate=" + format(settlementDate) +
                ", units=" + units +
                ", unit price=" + unitPrice +
                ']';
    }

}
