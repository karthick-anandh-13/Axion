package com.axion.kyc.verification;

import com.axion.kyc.entity.VerificationAction;
import com.axion.kyc.entity.VerificationSignal;
import com.axion.kyc.entity.KycVerification;

public interface VerificationHandler {

    VerificationAction getAction();

    VerificationSignal verify(
            KycVerification kyc
    );
}