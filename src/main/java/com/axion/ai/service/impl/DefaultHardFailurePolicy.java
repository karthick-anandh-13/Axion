package com.axion.ai.service.impl;

import org.springframework.stereotype.Service;

import com.axion.ai.service.HardFailurePolicy;
import com.axion.kyc.entity.VerificationSignal;
import com.axion.kyc.entity.VerificationSignalResult;

@Service
public class DefaultHardFailurePolicy
        implements HardFailurePolicy {

    @Override
    public boolean isHardFailure(
            VerificationSignal signal) {

        if (signal.getResult()
                != VerificationSignalResult.FAIL) {

            return false;
        }

        return switch (signal.getSignalType()) {

            case IDENTITY_MATCH,
                 FACE_MATCH,
                 DOCUMENT_EXPIRY,
                 DOCUMENT_TYPE,
                 DUPLICATE_DOCUMENT -> true;

            case FRAUD_RISK,
                 GRAPH_RISK -> signal.getConfidence() >= 0.80;

            default -> false;
        };
    }
}