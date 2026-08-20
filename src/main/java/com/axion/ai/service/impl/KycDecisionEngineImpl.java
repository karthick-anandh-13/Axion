package com.axion.ai.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axion.ai.dto.HybridDecision;
import com.axion.ai.dto.KycDecisionResult;
import com.axion.ai.service.EvidenceFusionEngine;
import com.axion.ai.service.KycDecisionEngine;
import com.axion.kyc.entity.VerificationSignal;
import com.axion.kyc.repository.VerificationSignalRepository;

@Service
@Transactional(readOnly = true)
public class KycDecisionEngineImpl
        implements KycDecisionEngine {

    private final EvidenceFusionEngine evidenceFusionEngine;

    private final VerificationSignalRepository signalRepository;

    public KycDecisionEngineImpl(
            EvidenceFusionEngine evidenceFusionEngine,
            VerificationSignalRepository signalRepository) {

        this.evidenceFusionEngine =
                evidenceFusionEngine;

        this.signalRepository =
                signalRepository;
    }

    @Override
    public KycDecisionResult decide(
            UUID kycVerificationId) {

        List<VerificationSignal> signals =
                signalRepository
                        .findByKycVerificationId(
                                kycVerificationId
                        );

        HybridDecision hybridDecision =
                evidenceFusionEngine.evaluate(
                        kycVerificationId
                );

        List<String> reasons =
                new ArrayList<>(
                        hybridDecision.reasons()
                );

        List<String> warnings =
                new ArrayList<>(
                        hybridDecision.warnings()
                );

        String decision =
                hybridDecision.decision();

        boolean additionalVerificationRequired =
                "REVIEW_REQUIRED".equals(decision);

        String nextAction =
                additionalVerificationRequired
                        ? "NEXT_BEST_CHECK"
                        : null;

        return new KycDecisionResult(
                decision,
                hybridDecision.confidence(),
                reasons,
                warnings,
                signals.size(),
                additionalVerificationRequired,
                nextAction
        );
    }
}