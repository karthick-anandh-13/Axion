package com.axion.kyc.service;

import com.axion.kyc.entity.KycVerification;

public interface KycAutomationEngine {

    KycAutomationResult evaluate(
            KycVerification kyc
    );
}