package com.axion.borrowing.dto;

import java.math.BigDecimal;

import com.axion.borrowing.entity.EmploymentType;
import java.time.LocalDateTime;
import java.util.UUID;

import com.axion.borrowing.entity.IncomeVerificationStatus;

public record FinancialProfileResponse(

        UUID id,

        UUID borrowerId,

        BigDecimal monthlyIncome,

        BigDecimal monthlyExpenses,

        BigDecimal existingDebt,

        BigDecimal monthlyDebtObligation,

        BigDecimal estimatedMonthlyDisposableIncome,

        EmploymentType employmentType,

        String employerName,

        Integer employmentDurationMonths,

        IncomeVerificationStatus incomeVerificationStatus,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}