package com.axion.kyc.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axion.ai.dto.HybridDecision;
import com.axion.ai.service.EvidenceFusionEngine;
import com.axion.ai.service.KycDecisionEngine;
import com.axion.kyc.entity.KycDecision;
import com.axion.kyc.entity.KycVerification;
import com.axion.kyc.entity.VerificationAction;
import com.axion.kyc.entity.VerificationSignal;
import com.axion.kyc.repository.KycRepository;
import com.axion.kyc.repository.VerificationSignalRepository;
import com.axion.kyc.service.KycVerificationOrchestrator;
import com.axion.kyc.service.NextBestCheckEngine;
import com.axion.kyc.service.NextVerificationAction;
import com.axion.kyc.verification.VerificationHandler;
import com.axion.kyc.verification.VerificationHandlerRegistry;

@Service
@Transactional
public class KycVerificationOrchestratorImpl
        implements KycVerificationOrchestrator {

    private static final int MAX_STEPS = 8;
    private static final double MIN_DECISION_CONFIDENCE = 0.90;
    private final KycRepository kycRepository;
    private final NextBestCheckEngine nextBestCheckEngine;
    private final VerificationHandlerRegistry handlerRegistry;
    private final VerificationSignalRepository signalRepository;
    private final EvidenceFusionEngine evidenceFusionEngine;
    private final KycDecisionEngine kycDecisionEngine;

    public KycVerificationOrchestratorImpl(
            KycRepository kycRepository,
            NextBestCheckEngine nextBestCheckEngine,
            VerificationHandlerRegistry handlerRegistry,
            VerificationSignalRepository signalRepository,
            EvidenceFusionEngine evidenceFusionEngine,
            KycDecisionEngine kycDecisionEngine) {

        this.kycRepository = kycRepository;
        this.nextBestCheckEngine = nextBestCheckEngine;
        this.handlerRegistry = handlerRegistry;
        this.signalRepository = signalRepository;
        this.evidenceFusionEngine = evidenceFusionEngine;
        this.kycDecisionEngine = kycDecisionEngine;
    }

    @Override
    public KycDecision execute(
            UUID kycVerificationId) {

        KycVerification kyc =
                kycRepository.findById(
                        kycVerificationId
                ).orElseThrow(() ->
                        new IllegalArgumentException(
                                "KYC verification not found."
                        ));

        for (int step = 0;
             step < MAX_STEPS;
             step++) {

            NextVerificationAction nextAction =
                    nextBestCheckEngine.selectNextAction(
                            kycVerificationId
                    );

            if (nextAction.action()
                    == VerificationAction.COMPLETE) {

                HybridDecision decision =
                        evidenceFusionEngine.evaluate(
                                kycVerificationId
                        );

                return mapDecision(
                        decision.decision()
                );
            }

            VerificationHandler handler =
                    handlerRegistry.getHandler(
                            nextAction.action()
                    );

            VerificationSignal signal =
                    handler.verify(kyc);

            signalRepository.save(signal);
        }

        return KycDecision.REVIEW_REQUIRED;
        }

        private KycDecision mapDecision(
                String decision) {

            return switch (decision) {

                case "PASS" ->
                        KycDecision.PASS;

                case "FAIL" ->
                        KycDecision.FAIL;

                default ->
                        KycDecision.REVIEW_REQUIRED;
            };
        }
}