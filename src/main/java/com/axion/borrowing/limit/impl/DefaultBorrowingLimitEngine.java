package com.axion.borrowing.limit.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axion.asset.entity.Asset;
import com.axion.asset.entity.AssetStatus;
import com.axion.asset.repository.AssetRepository;
import com.axion.borrowing.entity.BorrowerFinancialProfile;
import com.axion.borrowing.limit.BorrowingLimitEngine;
import com.axion.borrowing.limit.BorrowingLimitPolicy;
import com.axion.borrowing.limit.BorrowingLimitResult;
import com.axion.borrowing.repository.BorrowerFinancialProfileRepository;

@Service
@Transactional(readOnly = true)
public class DefaultBorrowingLimitEngine
        implements BorrowingLimitEngine {

    private final BorrowerFinancialProfileRepository profileRepository;

    private final AssetRepository assetRepository;

    private final BorrowingLimitPolicy borrowingLimitPolicy;

    public DefaultBorrowingLimitEngine(
            BorrowerFinancialProfileRepository profileRepository,
            AssetRepository assetRepository,
            BorrowingLimitPolicy borrowingLimitPolicy) {

        this.profileRepository =
                profileRepository;

        this.assetRepository =
                assetRepository;

        this.borrowingLimitPolicy =
                borrowingLimitPolicy;
    }

    @Override
    public BorrowingLimitResult calculateLimit(
            UUID borrowerId) {

        BorrowerFinancialProfile profile =
                profileRepository
                        .findByBorrowerId(borrowerId)
                        .orElseThrow(
                                () -> new IllegalArgumentException(
                                        "Financial profile not found"
                                )
                        );

        List<Asset> verifiedAssets =
                assetRepository
                        .findByOwnerIdAndStatus(
                                borrowerId,
                                AssetStatus.VERIFIED
                        );

        BigDecimal verifiedAssetValue =
                calculateVerifiedAssetValue(
                        verifiedAssets
                );

        List<String> factors =
                new ArrayList<>();

        List<String> warnings =
                new ArrayList<>();

        factors.add(
                "Verified assets counted: "
                        + verifiedAssets.size()
        );

        BigDecimal monthlyIncome =
                profile.getMonthlyIncome();

        BigDecimal existingDebt =
                profile.getExistingDebt();

        BigDecimal theoreticalCapacity =
                calculateTheoreticalCapacity(
                        profile,
                        verifiedAssetValue
                );

        /*
         * Initial risk adjustment.
         *
         * This is deliberately conservative until
         * the actual risk engine is connected.
         */
        BigDecimal riskAdjustedCapacity =
                theoreticalCapacity
                        .multiply(
                                new BigDecimal("0.80")
                        )
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        );

        BigDecimal policyLimit =
                borrowingLimitPolicy
                        .calculatePolicyLimit(
                                monthlyIncome,
                                verifiedAssetValue,
                                existingDebt
                        );

        /*
         * Existing loan exposure will be connected
         * when the Loan module is implemented.
         */
        BigDecimal existingExposure =
                BigDecimal.ZERO;

        warnings.add(
                "Existing loan exposure is not yet connected."
        );

        BigDecimal finalLimit =
                riskAdjustedCapacity
                        .min(policyLimit)
                        .subtract(existingExposure);

        if (finalLimit.compareTo(BigDecimal.ZERO) < 0) {

            finalLimit =
                    BigDecimal.ZERO;
        }

        finalLimit =
                finalLimit.setScale(
                        2,
                        RoundingMode.HALF_UP
                );

        factors.add(
                "Verified asset value: "
                        + verifiedAssetValue
        );

        factors.add(
                "Monthly income: "
                        + monthlyIncome
        );

        factors.add(
                "Existing debt: "
                        + existingDebt
        );

        return new BorrowingLimitResult(
                theoreticalCapacity,
                riskAdjustedCapacity,
                policyLimit,
                existingExposure,
                finalLimit,
                factors,
                warnings
        );
    }

    private BigDecimal calculateVerifiedAssetValue(
            List<Asset> assets) {

        BigDecimal total =
                BigDecimal.ZERO;

        for (Asset asset : assets) {

            if (asset.getVerifiedValue() == null) {
                continue;
            }

            total =
                    total.add(
                            asset.getVerifiedValue()
                    );
        }

        return total.setScale(
                2,
                RoundingMode.HALF_UP
        );
    }

    private BigDecimal calculateTheoreticalCapacity(
            BorrowerFinancialProfile profile,
            BigDecimal verifiedAssetValue) {

        BigDecimal annualDisposableIncome =
                profile.getMonthlyIncome()
                        .subtract(
                                profile.getMonthlyExpenses()
                        )
                        .subtract(
                                profile.getMonthlyDebtObligation()
                        )
                        .multiply(
                                new BigDecimal("12")
                        );

        if (annualDisposableIncome
                .compareTo(BigDecimal.ZERO) < 0) {

            annualDisposableIncome =
                    BigDecimal.ZERO;
        }

        BigDecimal assetContribution =
                verifiedAssetValue
                        .multiply(
                                new BigDecimal("0.20")
                        );

        return annualDisposableIncome
                .add(assetContribution)
                .setScale(
                        2,
                        RoundingMode.HALF_UP
                );
    }
}