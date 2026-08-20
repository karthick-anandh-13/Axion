package com.axion.kyc.verification;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.axion.ai.dto.GraphRiskResult;
import com.axion.ai.service.GraphRiskEngine;
import com.axion.kyc.entity.KycVerification;
import com.axion.kyc.entity.VerificationAction;
import com.axion.kyc.entity.VerificationSignal;
import com.axion.kyc.entity.VerificationSignalResult;
import com.axion.kyc.entity.VerificationSignalType;

@Component
public class GraphRiskVerificationHandler
        implements VerificationHandler {

    private final GraphRiskEngine graphRiskEngine;

    public GraphRiskVerificationHandler(
            GraphRiskEngine graphRiskEngine) {

        this.graphRiskEngine =
                graphRiskEngine;
    }

    @Override
    public VerificationAction getAction() {

        return VerificationAction.GRAPH_RISK;
    }

    @Override
    public VerificationSignal verify(
            KycVerification kyc) {

        UUID customerId =
                kyc.getUser()
                        .getId();

        GraphRiskResult result =
                graphRiskEngine.evaluate(
                        customerId
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

        return VerificationSignal.builder()
                .kycVerification(kyc)
                .signalType(
                        VerificationSignalType.GRAPH_RISK
                )
                .result(signalResult)
                .confidence(
                        1.0 - result.riskScore()
                )
                .reason(result.reason())
                .source("postgres-graph-risk")
                .sourceVersion("0.1")
                .build();
    }
}