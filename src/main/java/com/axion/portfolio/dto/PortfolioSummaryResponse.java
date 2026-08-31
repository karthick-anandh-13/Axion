package com.axion.portfolio.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioSummaryResponse {

    private UUID customerId;
    private String customerName;

    private int totalLoans;
    private int activeLoans;

    private BigDecimal totalBorrowed;
    private BigDecimal outstandingBalance;
    private BigDecimal totalAssetsValue;
}