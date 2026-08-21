package com.axion.kyc.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axion.ai.dto.VerificationContext;
import com.axion.ai.service.AdaptiveActionScorer;
import com.axion.kyc.entity.VerificationAction;
import com.axion.kyc.entity.VerificationSignal;
import com.axion.kyc.entity.VerificationSignalResult;
import com.axion.kyc.entity.VerificationSignalType;
import com.axion.kyc.repository.VerificationSignalRepository;
import com.axion.kyc.service.NextBestCheckEngine;
import com.axion.kyc.service.NextVerificationAction;
import com.axion.kyc.service.VerificationActionCost;

@Service
@Transactional(readOnly = true)
public class NextBestCheckEngineImpl implements NextBestCheckEngine {

    private final VerificationSignalRepository signalRepository;
    private final AdaptiveActionScorer adaptiveActionScorer;

    public NextBestCheckEngineImpl(
            VerificationSignalRepository signalRepository,
            AdaptiveActionScorer adaptiveActionScorer) {

        this.signalRepository = signalRepository;
        this.adaptiveActionScorer = adaptiveActionScorer;
    }

    @Override
    public @NonNull NextVerificationAction selectNextAction(
            @Nullable UUID kycVerificationId) {

        List<NextVerificationAction> actions = rankActions(kycVerificationId);

        return actions.stream()
                .findFirst()
                .orElse(new NextVerificationAction(
                        VerificationAction.COMPLETE,
                        0.0,
                        0.0,
                        0.0,
                        "Sufficient verification evidence."
                ));
    }

    @Override
    public @NonNull List<NextVerificationAction> rankActions(
            @Nullable UUID kycVerificationId) {

        List<VerificationSignal> signals =
                signalRepository.findByKycVerificationId(kycVerificationId);

        VerificationContext context = buildContext(signals);

        List<NextVerificationAction> actions = new ArrayList<>();

        addActionIfNeeded(actions, signals,
                VerificationAction.OCR,
                VerificationSignalType.OCR,
                0.85);

        addActionIfNeeded(actions, signals,
                VerificationAction.IDENTITY_MATCH,
                VerificationSignalType.IDENTITY_MATCH,
                0.90);

        addActionIfNeeded(actions, signals,
                VerificationAction.FACE_MATCH,
                VerificationSignalType.FACE_MATCH,
                0.90);

        addActionIfNeeded(actions, signals,
                VerificationAction.FRAUD_RISK,
                VerificationSignalType.FRAUD_RISK,
                0.90);

        addActionIfNeeded(actions, signals,
                VerificationAction.GRAPH_RISK,
                VerificationSignalType.GRAPH_RISK,
                0.90);

        // Re-score actions using the adaptive AI scorer
        actions.replaceAll(action -> {
            double score = adaptiveActionScorer.score(
                    action.action(),
                    context
            );

            return new NextVerificationAction(
                    action.action(),
                    action.expectedInformationGain(), // FIXED
                    action.cost(),
                    score,
                    action.reason()
            );
        });

        actions.sort(
                Comparator.comparingDouble(
                        NextVerificationAction::priority
                ).reversed()
        );

        return actions;
    }

    private void addActionIfNeeded(
            List<NextVerificationAction> actions,
            List<VerificationSignal> signals,
            VerificationAction action,
            VerificationSignalType signalType,
            double requiredConfidence) {

        VerificationSignal latestSignal =
                findLatestSignal(signals, signalType);

        if (latestSignal == null) {

            double informationGain =
                    estimateInformationGain(action);

            double cost =
                    VerificationActionCost.getCost(action);

            actions.add(new NextVerificationAction(
                    action,
                    informationGain,
                    cost,
                    informationGain / cost,
                    "Verification has not been performed."
            ));

            return;
        }

        if (latestSignal.getResult() == VerificationSignalResult.UNCERTAIN
                || latestSignal.getConfidence() < requiredConfidence) {

            double informationGain =
                    estimateInformationGain(action)
                            * uncertaintyFactor(latestSignal.getConfidence());

            double cost =
                    VerificationActionCost.getCost(action);

            actions.add(new NextVerificationAction(
                    action,
                    informationGain,
                    cost,
                    informationGain / cost,
                    "Existing evidence is insufficient."
            ));
        }
    }

    private VerificationSignal findLatestSignal(
            List<VerificationSignal> signals,
            VerificationSignalType type) {

        return signals.stream()
                .filter(signal -> signal.getSignalType() == type)
                .max(Comparator.comparing(VerificationSignal::getCreatedAt))
                .orElse(null);
    }

    private double estimateInformationGain(
            VerificationAction action) {

        return switch (action) {
            case OCR -> 0.25;
            case IDENTITY_MATCH -> 0.30;
            case FACE_MATCH -> 0.45;
            case FRAUD_RISK -> 0.40;
            case GRAPH_RISK -> 0.35;
            case EXTERNAL_VERIFICATION -> 0.60;
            case COMPLETE -> 0.0;
        };
    }

    private double uncertaintyFactor(double confidence) {
        return Math.max(0.1, 1.0 - confidence);
    }

    private VerificationContext buildContext(
            List<VerificationSignal> signals) {

        Set<VerificationSignalType> completed = new HashSet<>();
        Set<VerificationSignalType> failed = new HashSet<>();
        Set<VerificationSignalType> uncertain = new HashSet<>();

        double totalConfidence = 0.0;

        for (VerificationSignal signal : signals) {
            totalConfidence += signal.getConfidence();

            switch (signal.getResult()) {
                case PASS -> completed.add(signal.getSignalType());
                case FAIL -> failed.add(signal.getSignalType());
                case UNCERTAIN -> uncertain.add(signal.getSignalType());
                case NOT_EVALUATED -> {
                }
            }
        }

        double overallConfidence = signals.isEmpty()
                ? 0.0
                : totalConfidence / signals.size();

        return new VerificationContext(
                overallConfidence,
                1.0 - overallConfidence,
                completed,
                failed,
                uncertain
        );
    }
}