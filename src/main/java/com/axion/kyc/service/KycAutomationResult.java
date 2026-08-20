package com.axion.kyc.service;

import com.axion.kyc.entity.KycDecision;

public record KycAutomationResult(
        KycDecision decision,
        double confidenceScore,
        String reason
) {
}