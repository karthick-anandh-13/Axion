package com.axion.kyc.service;

import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;

public interface NextBestCheckEngine {

    @NonNull
    NextVerificationAction selectNextAction(UUID kycVerificationId);

    @NonNull
    List<NextVerificationAction> rankActions(@NonNull UUID kycVerificationId);
}