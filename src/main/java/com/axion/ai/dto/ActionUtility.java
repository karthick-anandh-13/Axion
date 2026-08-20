package com.axion.ai.dto;

import com.axion.kyc.entity.VerificationAction;

public record ActionUtility(

        VerificationAction action,

        double expectedInformationGain,

        double cost,

        double riskReduction,

        double utility,

        String reason

) {
}