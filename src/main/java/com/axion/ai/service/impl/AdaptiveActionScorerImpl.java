package com.axion.ai.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.axion.ai.dto.ActionUtility;
import com.axion.ai.dto.VerificationContext;
import com.axion.ai.service.AdaptiveActionScorer;
import com.axion.kyc.entity.VerificationAction;
import com.axion.kyc.entity.VerificationSignalType;
import com.axion.kyc.service.VerificationActionCost;

@Service
public class AdaptiveActionScorerImpl implements AdaptiveActionScorer {

    @Override
    public @NonNull List<ActionUtility> rankActions(
            @NonNull VerificationContext context) {

        List<ActionUtility> actions = new ArrayList<>();

        for (VerificationAction action : VerificationAction.values()) {

            if (action == VerificationAction.COMPLETE) {
                continue;
            }

            VerificationSignalType signalType = mapToSignalType(action);

            if (signalType == null) {
                continue;
            }

            if (context.completedSignals().contains(signalType)) {
                continue;
            }

            double informationGain =
                    calculateInformationGain(action, context);

            double riskReduction =
                    calculateRiskReduction(action);

            double cost =
                    VerificationActionCost.getCost(action);

            double utility =
                    cost == 0.0
                            ? 0.0
                            : (informationGain * riskReduction) / cost;

            actions.add(new ActionUtility(
                    action,
                    informationGain,
                    cost,
                    riskReduction,
                    utility,
                    buildReason(action, context)
            ));
        }

        actions.sort(
                Comparator.comparingDouble(ActionUtility::utility)
                        .reversed()
        );

        return actions;
    }

    @Override
    public @NonNull ActionUtility bestAction(
            @NonNull VerificationContext context) {

        return rankActions(context)
                .stream()
                .findFirst()
                .orElse(new ActionUtility(
                        VerificationAction.COMPLETE,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        "No additional verification is required."
                ));
    }

    @Override
    public double score(
            @NonNull VerificationAction action,
            @NonNull VerificationContext context) {

        if (action == VerificationAction.COMPLETE) {
            return 0.0;
        }

        double informationGain =
                calculateInformationGain(action, context);

        double riskReduction =
                calculateRiskReduction(action);

        double cost =
                VerificationActionCost.getCost(action);

        return cost == 0.0
                ? 0.0
                : (informationGain * riskReduction) / cost;
    }

    @Override
    public double score1(
            @NonNull VerificationAction action,
            @NonNull VerificationContext context) {

        return score(action, context);
    }

    private double calculateInformationGain(
            VerificationAction action,
            VerificationContext context) {

        double baseGain = switch (action) {
            case OCR -> 0.25;
            case IDENTITY_MATCH -> 0.30;
            case FACE_MATCH -> 0.45;
            case FRAUD_RISK -> 0.40;
            case GRAPH_RISK -> 0.35;
            case EXTERNAL_VERIFICATION -> 0.60;
            case COMPLETE -> 0.0;
        };

        return baseGain * (0.5 + context.uncertainty());
    }

    private double calculateRiskReduction(
            VerificationAction action) {

        return switch (action) {
            case OCR -> 0.60;
            case IDENTITY_MATCH -> 0.90;
            case FACE_MATCH -> 0.95;
            case FRAUD_RISK -> 0.90;
            case GRAPH_RISK -> 0.75;
            case EXTERNAL_VERIFICATION -> 1.00;
            case COMPLETE -> 0.0;
        };
    }

    private VerificationSignalType mapToSignalType(
            VerificationAction action) {

        return switch (action) {
            case OCR -> VerificationSignalType.OCR;
            case IDENTITY_MATCH -> VerificationSignalType.IDENTITY_MATCH;
            case FACE_MATCH -> VerificationSignalType.FACE_MATCH;
            case FRAUD_RISK -> VerificationSignalType.FRAUD_RISK;
            case GRAPH_RISK -> VerificationSignalType.GRAPH_RISK;
            case EXTERNAL_VERIFICATION -> VerificationSignalType.EXTERNAL_VERIFICATION;
            case COMPLETE -> null;
        };
    }

    private String buildReason(
            VerificationAction action,
            VerificationContext context) {

        return action + " provides additional evidence. Current uncertainty: "
                + Math.round(context.uncertainty() * 100) + "%.";
    }
}