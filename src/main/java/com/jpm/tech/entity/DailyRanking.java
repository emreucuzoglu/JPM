package com.jpm.tech.entity;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
public class DailyRanking {
    private LocalDate settlementDate;
    private String entityName;
    private int rank;
    private double amount;
}
