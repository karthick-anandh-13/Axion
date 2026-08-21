package com.axion.borrowing.dto;

import java.math.BigDecimal;

import com.axion.borrowing.entity.EmploymentType;

import jakarta.validation.constraints.*;

public record CreateFinancialProfileRequest(

        @NotNull(message = "Monthly income is required")
        @DecimalMin(
                value = "0.0",
                message = "Monthly income cannot be negative"
        )
        BigDecimal monthlyIncome,

        @NotNull(message = "Monthly expenses are required")
        @DecimalMin(
                value = "0.0",
                message = "Monthly expenses cannot be negative"
        )
        BigDecimal monthlyExpenses,

        @NotNull(message = "Existing debt is required")
        @DecimalMin(
                value = "0.0",
                message = "Existing debt cannot be negative"
        )
        BigDecimal existingDebt,

        @NotNull(message = "Monthly debt obligation is required")
        @DecimalMin(
                value = "0.0",
                message = "Monthly debt obligation cannot be negative"
        )
        BigDecimal monthlyDebtObligation,

        @NotNull(message = "Employment type is required")
        EmploymentType employmentType,

        @Size(
                max = 100,
                message = "Employer name cannot exceed 100 characters"
        )
        String employerName,

        @Min(
                value = 0,
                message = "Employment duration cannot be negative"
        )
        Integer employmentDurationMonths
) {
}