package com.axion.repayment.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.lang.NonNull;

import jakarta.validation.constraints.NotNull;

public record RepaymentSummaryResponse(

        @NotNull
        @NonNull
        BigDecimal totalOutstanding,

        @NotNull
        @NonNull
        BigDecimal totalPaid,

        @NotNull
        @NonNull
        BigDecimal nextEmiAmount,

        @NotNull
        @NonNull
        LocalDate nextDueDate,

        @NotNull
        Integer activeLoans,

        @NotNull
        Integer pendingInstallments,

        @NotNull
        Integer overdueInstallments
) {}