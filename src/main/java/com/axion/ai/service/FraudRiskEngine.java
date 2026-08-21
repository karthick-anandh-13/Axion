package com.axion.ai.service;

import com.axion.ai.dto.FraudRiskResult;
import com.axion.kyc.entity.KycVerification;

public interface FraudRiskEngine {
    FraudRiskResult evaluate(KycVerification kycVerification);
}
