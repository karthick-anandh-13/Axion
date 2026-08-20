package com.axion.kyc.service;

import com.axion.kyc.entity.VerificationAction;

public record NextVerificationAction(
        VerificationAction action,
        double expectedInformationGain,
        double cost,
        double priority,
        String reason
) {
}