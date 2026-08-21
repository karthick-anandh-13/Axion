package com.axion.ai.service;

import com.axion.kyc.entity.VerificationSignal;

public interface HardFailurePolicy {
    boolean isHardFailure(VerificationSignal signal);
}
