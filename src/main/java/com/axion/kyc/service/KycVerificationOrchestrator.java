package com.axion.kyc.service;

import java.util.UUID;

import com.axion.kyc.entity.KycDecision;

public interface KycVerificationOrchestrator {

    KycDecision execute(
            UUID kycVerificationId
    );
}