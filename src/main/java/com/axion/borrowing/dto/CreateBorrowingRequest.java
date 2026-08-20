package com.axion.borrowing.dto;

import java.math.BigDecimal;

import com.axion.borrowing.entity.BorrowingPurpose;

import jakarta.validation.constraints.*;

public record CreateBorrowingRequest(

        @NotNull(message = "Borrowing purpose is required")
        BorrowingPurpose purpose,

        @Size(
                max = 1000,
                message = "Purpose description cannot exceed 1000 characters"
        )
        String purposeDescription,

        @NotNull(message = "Requested amount is required")
        @DecimalMin(
                value = "1000.00",
                message = "Minimum borrowing amount is 1000"
        )
        BigDecimal requestedAmount,

        @NotNull(message = "Tenure is required")
        @Min(
                value = 1,
                message = "Tenure must be at least 1 month"
        )
        @Max(
                value = 120,
                message = "Tenure cannot exceed 120 months"
        )
        Integer requestedTenureMonths,

        @DecimalMin(
                value = "0.0",
                message = "APR cannot be negative"
        )
        @DecimalMax(
                value = "100.0",
                message = "APR cannot exceed 100%"
        )
        BigDecimal maximumAcceptableApr

) {
}