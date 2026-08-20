package com.axion.borrowing.limit;

import java.math.BigDecimal;
import java.util.List;

public record BorrowingLimitResult(

        BigDecimal maximumTheoreticalCapacity,

        BigDecimal riskAdjustedCapacity,

        BigDecimal policyLimit,

        BigDecimal existingExposure,

        BigDecimal finalBorrowingLimit,

        List<String> factors,

        List<String> warnings

) {
}