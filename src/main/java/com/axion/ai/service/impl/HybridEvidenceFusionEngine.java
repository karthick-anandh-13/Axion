package com.axion.ai.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axion.ai.dto.HybridDecision;
import com.axion.ai.service.EvidenceFusionEngine;
import com.axion.kyc.entity.VerificationSignal;
import com.axion.kyc.entity.VerificationSignalResult;
import com.axion.kyc.entity.VerificationSignalType;
import com.axion.kyc.repository.VerificationSignalRepository;

@Service
@Transactional(readOnly = true)
public class HybridEvidenceFusionEngine
        implements EvidenceFusionEngine {

    private static final double PASS_THRESHOLD = 0.90;
    private static final double FAIL_THRESHOLD = 0.30;

    private final VerificationSignalRepository signalRepository;

    public HybridEvidenceFusionEngine(
            VerificationSignalRepository signalRepository) {

        this.signalRepository = signalRepository;
    }

    @Override
    public HybridDecision evaluate(
            UUID kycVerificationId) {

        List<VerificationSignal> signals =
                signalRepository
                        .findByKycVerificationId(
                                kycVerificationId
                        );

        if (signals.isEmpty()) {

            return new HybridDecision(
                    "REVIEW_REQUIRED",
                    0.0,
                    List.of(
                            "No verification evidence available."
                    ),
                    List.of(
                            "Additional verification required."
                    )
            );
        }

        List<String> reasons =
                new ArrayList<>();

        List<String> warnings =
                new ArrayList<>();

        double weightedScore = 0.0;
        double totalWeight = 0.0;

        boolean hardFailure = false;

        for (VerificationSignal signal : signals) {

            double weight =
                    getSignalWeight(
                            signal.getSignalType()
                    );

            double evidenceScore =
                    convertToEvidenceScore(
                            signal
                    );

            weightedScore +=
                    evidenceScore * weight;

            totalWeight += weight;

            if (signal.getResult()
                    == VerificationSignalResult.FAIL) {

                hardFailure = true;

                warnings.add(
                        signal.getSignalType()
                                + " verification failed."
                );
            }

            if (signal.getReason() != null) {

                reasons.add(
                        signal.getSignalType()
                                + ": "
                                + signal.getReason()
                );
            }
        }

        double confidence =
                totalWeight == 0.0
                        ? 0.0
                        : weightedScore / totalWeight;

        String decision;

        if (hardFailure) {

            decision = "FAIL";

        } else if (confidence >= PASS_THRESHOLD) {

            decision = "PASS";

        } else if (confidence <= FAIL_THRESHOLD) {

            decision = "FAIL";

        } else {

            decision = "REVIEW_REQUIRED";

            warnings.add(
                    "Evidence confidence is insufficient."
            );
        }

        return new HybridDecision(
                decision,
                confidence,
                reasons,
                warnings
        );
    }

    private double convertToEvidenceScore(
            VerificationSignal signal) {

        if (signal.getResult()
                == VerificationSignalResult.PASS) {

            return signal.getConfidence();
        }

        if (signal.getResult()
                == VerificationSignalResult.UNCERTAIN) {

            return signal.getConfidence() * 0.50;
        }

        if (signal.getResult()
                == VerificationSignalResult.FAIL) {

            return 0.0;
        }

        return 0.0;
    }

    private double getSignalWeight(
            VerificationSignalType type) {

        return switch (type) {

            case FILE_INTEGRITY -> 0.10;

            case DOCUMENT_TYPE -> 0.10;

            case DOCUMENT_EXPIRY -> 0.10;

            case OCR -> 0.10;

            case IDENTITY_MATCH -> 0.25;

            case FACE_MATCH -> 0.25;

            case DUPLICATE_DOCUMENT -> 0.15;

            case FRAUD_RISK -> 0.20;

            case GRAPH_RISK -> 0.15;

            case EXTERNAL_VERIFICATION -> 0.25;
        };
    }
}