package com.jpm.tech.entity;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
public class DailyReport {
    private LocalDate settlementDate;
    private double totalBuy;
    private double totalSell;
}
