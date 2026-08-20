package com.axion.kyc.service;

import java.util.List;
import java.util.UUID;

public interface NextBestCheckEngine {

    NextVerificationAction selectNextAction(
            UUID kycVerificationId
    );

    List<NextVerificationAction> rankActions(
            UUID kycVerificationId
    );
}