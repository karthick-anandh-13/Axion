package com.axion.kyc.service;

import java.util.UUID;

import org.springframework.lang.NonNull;

import com.axion.kyc.entity.KycDecision;

public interface KycVerificationOrchestrator {

    @NonNull
    KycDecision execute(
            @NonNull UUID kycVerificationId
    );
}