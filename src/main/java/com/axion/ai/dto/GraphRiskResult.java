package com.axion.ai.dto;

import java.util.List;

public record GraphRiskResult(
        double riskScore,
        String riskLevel,
        int connectedEntities,
        int suspiciousConnections,
        List<String> riskFactors,
        String reason
) {
}