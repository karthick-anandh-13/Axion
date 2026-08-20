package com.axion.ai.dto;

import java.util.List;

public record FraudRiskResult(
        double riskScore,
        String riskLevel,
        List<String> riskFactors,
        String reason
) {
}