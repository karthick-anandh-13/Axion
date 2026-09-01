package com.axion.reporting.dto;

import java.math.BigDecimal;

import org.springframework.lang.NonNull;

import jakarta.validation.constraints.NotNull;

public record DashboardReportResponse(

        @NotNull
        Integer totalLoans,

        @NotNull
        Integer activeLoans,

        @NotNull
        Integer completedLoans,

        @NotNull
        Integer overdueLoans,

        @NotNull
        @NonNull
        BigDecimal totalBorrowed,

        @NotNull
        @NonNull
        BigDecimal totalRepaid,

        @NotNull
        @NonNull
        BigDecimal outstandingBalance,

        @NotNull
        @NonNull
        BigDecimal monthlyEmi,

        @NotNull
        Integer totalNotifications

) {}