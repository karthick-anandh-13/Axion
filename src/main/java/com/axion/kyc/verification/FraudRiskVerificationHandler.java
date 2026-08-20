package com.axion.kyc.verification;

import org.springframework.stereotype.Component;

import com.axion.ai.dto.FraudRiskResult;
import com.axion.ai.service.FraudRiskEngine;
import com.axion.kyc.entity.KycVerification;
import com.axion.kyc.entity.VerificationAction;
import com.axion.kyc.entity.VerificationSignal;
import com.axion.kyc.entity.VerificationSignalResult;
import com.axion.kyc.entity.VerificationSignalType;

@Component
public class FraudRiskVerificationHandler
        implements VerificationHandler {

    private final FraudRiskEngine fraudRiskEngine;

    public FraudRiskVerificationHandler(
            FraudRiskEngine fraudRiskEngine) {

        this.fraudRiskEngine =
                fraudRiskEngine;
    }

    @Override
    public VerificationAction getAction() {

        return VerificationAction.FRAUD_RISK;
    }

    @Override
    public VerificationSignal verify(
            KycVerification kyc) {

        FraudRiskResult result =
                fraudRiskEngine.evaluate(
                        kyc
                );

        VerificationSignalResult signalResult;

        if (result.riskScore() >= 0.80) {

            signalResult =
                    VerificationSignalResult.FAIL;

        } else if (result.riskScore() >= 0.50) {

            signalResult =
                    VerificationSignalResult.UNCERTAIN;

        } else {

            signalResult =
                    VerificationSignalResult.PASS;
        }

        double confidence =
                1.0 - result.riskScore();

        return VerificationSignal.builder()
                .kycVerification(kyc)
                .signalType(
                        VerificationSignalType.FRAUD_RISK
                )
                .result(signalResult)
                .confidence(confidence)
                .reason(result.reason())
                .source("rule-fraud-engine")
                .sourceVersion("0.1")
                .build();
    }
}