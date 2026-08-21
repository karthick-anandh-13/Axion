package com.axion.ai.service;

import java.util.UUID;
import com.axion.ai.dto.KycDecisionResult;

public interface KycDecisionEngine {
    KycDecisionResult decide(UUID kycVerificationId);
}
