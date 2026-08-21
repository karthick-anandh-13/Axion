package com.axion.ai.service;

import java.util.List;

import org.springframework.lang.NonNull;

import com.axion.ai.dto.ActionUtility;
import com.axion.ai.dto.VerificationContext;
import com.axion.kyc.entity.VerificationAction;

public interface AdaptiveActionScorer {

    @NonNull
    List<ActionUtility> rankActions(
            @NonNull VerificationContext context
    );

    @NonNull
    ActionUtility bestAction(
            @NonNull VerificationContext context
    );

    double score(
            @NonNull VerificationAction action,
            @NonNull VerificationContext context
    );

    double score1(
            @NonNull VerificationAction action,
            @NonNull VerificationContext context
    );
}