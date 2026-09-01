package com.axion.reporting.dto;

import java.math.BigDecimal;

import org.springframework.lang.NonNull;

import jakarta.validation.constraints.NotNull;

public record RevenueReportResponse(

        @NotNull
        @NonNull
        BigDecimal totalRevenue,

        @NotNull
        @NonNull
        BigDecimal platformFees,

        @NotNull
        @NonNull
        BigDecimal interestCollected,

        @NotNull
        Integer successfulPayments,

        @NotNull
        Integer failedPayments,

        @NotNull
        @NonNull
        BigDecimal averagePaymentAmount

) {}