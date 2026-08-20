package com.axion.kyc.service.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.axion.kyc.entity.KycDecision;
import com.axion.kyc.entity.KycDocument;
import com.axion.kyc.entity.KycVerification;
import com.axion.kyc.service.KycAutomationEngine;
import com.axion.kyc.service.KycAutomationResult;

@Service
public class KycAutomationEngineImpl
        implements KycAutomationEngine {

    @Override
    public KycAutomationResult evaluate(
            KycVerification kyc) {

        if (kyc == null) {
            return new KycAutomationResult(
                    KycDecision.FAIL,
                    0.0,
                    "KYC verification does not exist."
            );
        }

        if (kyc.getDocuments() == null
                || kyc.getDocuments().isEmpty()) {

            return new KycAutomationResult(
                    KycDecision.FAIL,
                    0.0,
                    "No KYC documents were submitted."
            );
        }

        for (KycDocument document : kyc.getDocuments()) {

            if (document.getStorageReference() == null
                    || document.getStorageReference().isBlank()) {

                return new KycAutomationResult(
                        KycDecision.FAIL,
                        0.0,
                        "One or more documents are missing storage data."
                );
            }

            if (document.getExpiryDate() != null
                    && document.getExpiryDate()
                    .isBefore(LocalDate.now())) {

                return new KycAutomationResult(
                        KycDecision.FAIL,
                        0.0,
                        "One or more KYC documents have expired."
                );
            }
        }

        return new KycAutomationResult(
                KycDecision.REVIEW_REQUIRED,
                0.50,
                "Basic automated validation passed. "
                + "Additional identity verification is required."
        );
    }
}