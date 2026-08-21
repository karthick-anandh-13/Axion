package com.axion.ai.service;

import java.util.UUID;
import com.axion.ai.dto.GraphRiskResult;

public interface GraphRiskEngine {
    GraphRiskResult evaluate(UUID kycVerificationId);
}
