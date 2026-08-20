package com.axion.ai.dto;

import java.util.List;

public record KycDecisionResult(

        String decision,

        double confidence,

        List<String> reasons,

        List<String> warnings,

        int signalsUsed,

        boolean additionalVerificationRequired,

        String nextRecommendedAction

) {
}