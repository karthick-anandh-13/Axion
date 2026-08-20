package com.axion.kyc.service;

import com.axion.kyc.entity.VerificationAction;

public final class VerificationActionCost {

    private VerificationActionCost() {
    }

    public static double getCost(
            VerificationAction action) {

        return switch (action) {

            case OCR -> 2.0;

            case IDENTITY_MATCH -> 1.0;

            case FACE_MATCH -> 5.0;

            case FRAUD_RISK -> 4.0;

            case GRAPH_RISK -> 3.0;

            case EXTERNAL_VERIFICATION -> 10.0;

            case COMPLETE -> 0.0;
        };
    }
}