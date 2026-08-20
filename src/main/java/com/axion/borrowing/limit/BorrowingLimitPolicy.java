package com.axion.borrowing.limit;

import java.math.BigDecimal;

public interface BorrowingLimitPolicy {

    BigDecimal calculatePolicyLimit(
            BigDecimal monthlyIncome,
            BigDecimal verifiedAssetValue,
            BigDecimal existingDebt
    );

    BigDecimal getMaximumAbsoluteLimit();
}