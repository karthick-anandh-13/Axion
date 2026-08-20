package com.axion.kyc.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.Set;

import com.axion.ai.dto.VerificationContext;

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
public class NextBestCheckEngineImpl
        implements NextBestCheckEngine {

    private static final double COMPLETE_THRESHOLD = 0.90;

    private final VerificationSignalRepository signalRepository;
    private final AdaptiveActionScorer adaptiveActionScorer;

    public NextBestCheckEngineImpl(
            VerificationSignalRepository signalRepository
            ,
            AdaptiveActionScorer adaptiveActionScorer) {

        this.signalRepository = signalRepository;
        this.adaptiveActionScorer =
            adaptiveActionScorer;
    }

    @Override
    public NextVerificationAction selectNextAction(
            UUID kycVerificationId) {

        List<NextVerificationAction> actions =
                rankActions(kycVerificationId);

        return actions.stream()
                .findFirst()
                .orElse(
                        new NextVerificationAction(
                                VerificationAction.COMPLETE,
                                0.0,
                                0.0,
                                0.0,
                                "Sufficient verification evidence."
                        )
                );
    }

    @Override
    public List<NextVerificationAction> rankActions(
            UUID kycVerificationId) {

        List<VerificationSignal> signals =
                signalRepository
                        .findByKycVerificationId(
                                kycVerificationId
                        );

        List<NextVerificationAction> actions =
                new ArrayList<>();

        addActionIfNeeded(
                actions,
                signals,
                VerificationAction.OCR,
                VerificationSignalType.OCR,
                0.85
        );

        addActionIfNeeded(
                actions,
                signals,
                VerificationAction.IDENTITY_MATCH,
                VerificationSignalType.IDENTITY_MATCH,
                0.90
        );

        addActionIfNeeded(
                actions,
                signals,
                VerificationAction.FACE_MATCH,
                VerificationSignalType.FACE_MATCH,
                0.90
        );

        addActionIfNeeded(
                actions,
                signals,
                VerificationAction.FRAUD_RISK,
                VerificationSignalType.FRAUD_RISK,
                0.90
        );

        addActionIfNeeded(
                actions,
                signals,
                VerificationAction.GRAPH_RISK,
                VerificationSignalType.GRAPH_RISK,
                0.90
        );

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
                findLatestSignal(
                        signals,
                        signalType
                );

        if (latestSignal == null) {

            double informationGain =
                    estimateInformationGain(
                            action
                    );

            double cost =
                    VerificationActionCost.getCost(
                            action
                    );

            double priority =
                    informationGain / cost;

            actions.add(
                    new NextVerificationAction(
                            action,
                            informationGain,
                            cost,
                            priority,
                            "Verification has not been performed."
                    )
            );

            return;
        }

        if (latestSignal.getResult()
                == VerificationSignalResult.UNCERTAIN
                || latestSignal.getConfidence()
                < requiredConfidence) {

            double informationGain =
                    estimateInformationGain(action)
                    * uncertaintyFactor(
                            latestSignal.getConfidence()
                    );

            double cost =
                    VerificationActionCost.getCost(action);

            double priority =
                    informationGain / cost;

            actions.add(
                    new NextVerificationAction(
                            action,
                            informationGain,
                            cost,
                            priority,
                            "Existing evidence is insufficient."
                    )
            );
        }
    }

    private VerificationSignal findLatestSignal(
            List<VerificationSignal> signals,
            VerificationSignalType type) {

        return signals.stream()
                .filter(signal ->
                        signal.getSignalType() == type
                )
                .max(
                        Comparator.comparing(
                                VerificationSignal::getCreatedAt
                        )
                )
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

    private double uncertaintyFactor(
            double confidence) {

        return Math.max(
                0.1,
                1.0 - confidence
        );
    }

    private VerificationContext buildContext(
        List<VerificationSignal> signals) {

    Set<VerificationSignalType> completed =
            new HashSet<>();

    Set<VerificationSignalType> failed =
            new HashSet<>();

    Set<VerificationSignalType> uncertain =
            new HashSet<>();

    double totalConfidence = 0.0;

    for (VerificationSignal signal : signals) {

        totalConfidence +=
                signal.getConfidence();

        switch (signal.getResult()) {

            case PASS ->
                    completed.add(
                            signal.getSignalType()
                    );

            case FAIL ->
                    failed.add(
                            signal.getSignalType()
                    );

            case UNCERTAIN ->
                    uncertain.add(
                            signal.getSignalType()
                    );

            case NOT_EVALUATED -> {
                // Nothing to add.
            }
        }
    }

    double overallConfidence =
            signals.isEmpty()
                    ? 0.0
                    : totalConfidence
                        / signals.size();

    double uncertainty =
            1.0 - overallConfidence;

    return new VerificationContext(
            overallConfidence,
            uncertainty,
            completed,
            failed,
            uncertain
    );
}
}