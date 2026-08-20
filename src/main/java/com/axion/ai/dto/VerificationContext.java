package com.axion.ai.dto;

import java.util.Set;

import com.axion.kyc.entity.VerificationSignalType;

public record VerificationContext(

        double overallConfidence,

        double uncertainty,

        Set<VerificationSignalType> completedSignals,

        Set<VerificationSignalType> failedSignals,

        Set<VerificationSignalType> uncertainSignals

) {
}