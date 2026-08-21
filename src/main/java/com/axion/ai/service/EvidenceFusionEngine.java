package com.axion.ai.service;

import java.util.UUID;
import com.axion.ai.dto.HybridDecision;

public interface EvidenceFusionEngine {
    HybridDecision evaluate(UUID kycVerificationId);
}
