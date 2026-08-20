package com.axion.kyc.service;

import java.util.List;
import java.util.UUID;

import com.axion.kyc.entity.VerificationSignal;
import com.axion.kyc.entity.VerificationSignalType;
import com.axion.kyc.entity.VerificationSignalResult;

public interface VerificationSignalService {

    VerificationSignal recordSignal(
            UUID kycVerificationId,
            VerificationSignalType signalType,
            VerificationSignalResult result,
            double confidence,
            String reason,
            String source,
            String sourceVersion
    );

    List<VerificationSignal> getSignals(
            UUID kycVerificationId
    );
}