package com.axion.borrowing.limit.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

import com.axion.borrowing.limit.BorrowingLimitPolicy;

@Component
public class DefaultBorrowingLimitPolicy
        implements BorrowingLimitPolicy {

    private static final BigDecimal INCOME_MULTIPLIER =
            new BigDecimal("12");

    private static final BigDecimal ASSET_FACTOR =
            new BigDecimal("0.20");

    private static final BigDecimal DEBT_FACTOR =
            new BigDecimal("0.50");

    private static final BigDecimal MAX_LIMIT =
            new BigDecimal("10000000");

    @Override
    public BigDecimal calculatePolicyLimit(
            BigDecimal monthlyIncome,
            BigDecimal verifiedAssetValue,
            BigDecimal existingDebt) {

        BigDecimal annualIncomeCapacity =
                monthlyIncome.multiply(
                        INCOME_MULTIPLIER
                );

        BigDecimal assetCapacity =
                verifiedAssetValue.multiply(
                        ASSET_FACTOR
                );

        BigDecimal debtAdjustment =
                existingDebt.multiply(
                        DEBT_FACTOR
                );

        BigDecimal result =
                annualIncomeCapacity
                        .add(assetCapacity)
                        .subtract(debtAdjustment);

        if (result.compareTo(BigDecimal.ZERO) < 0) {

            result = BigDecimal.ZERO;
        }

        return result.min(MAX_LIMIT)
                .setScale(
                        2,
                        RoundingMode.HALF_UP
                );
    }

    @Override
    public BigDecimal getMaximumAbsoluteLimit() {

        return MAX_LIMIT;
    }
}