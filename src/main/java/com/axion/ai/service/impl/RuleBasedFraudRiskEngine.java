package com.axion.ai.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.axion.ai.dto.FraudRiskResult;
import com.axion.ai.service.FraudRiskEngine;
import com.axion.kyc.entity.KycVerification;

@Service
public class RuleBasedFraudRiskEngine
        implements FraudRiskEngine {

    @Override
    public FraudRiskResult evaluate(
            KycVerification kycVerification) {

        double riskScore = 0.0;

        List<String> riskFactors =
                new ArrayList<>();

        /*
         * Rule 1:
         * No documents available.
         */
        if (kycVerification.getDocuments() == null
                || kycVerification.getDocuments().isEmpty()) {

            riskScore += 0.40;

            riskFactors.add(
                    "No KYC documents available."
            );
        }

        /*
         * Rule 2:
         * Too many verification signals
         * can indicate repeated processing.
         *
         * This is only a placeholder rule.
         */
        if (kycVerification.getSignals() != null
                && kycVerification.getSignals().size() > 10) {

            riskScore += 0.20;

            riskFactors.add(
                    "Unusually high verification activity."
            );
        }

        riskScore =
                Math.min(
                        riskScore,
                        1.0
                );

        String riskLevel =
                determineRiskLevel(
                        riskScore
                );

        String reason =
                riskFactors.isEmpty()
                        ? "No rule-based risk indicators detected."
                        : "Rule-based risk indicators detected.";

        return new FraudRiskResult(
                riskScore,
                riskLevel,
                riskFactors,
                reason
        );
    }

    private String determineRiskLevel(
            double riskScore) {

        if (riskScore >= 0.80) {
            return "HIGH";
        }

        if (riskScore >= 0.50) {
            return "MEDIUM";
        }

        return "LOW";
    }
}