package com.axion.reporting.dto;

import java.math.BigDecimal;

import org.springframework.lang.NonNull;

import jakarta.validation.constraints.NotNull;

public record LoanReportResponse(

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
        BigDecimal totalPrincipal,

        @NotNull
        @NonNull
        BigDecimal totalOutstanding,

        @NotNull
        @NonNull
        BigDecimal averageApr,

        @NotNull
        @NonNull
        BigDecimal averageEmi

) {}