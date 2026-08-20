package com.axion.kyc.verification;

import org.springframework.stereotype.Component;

import com.axion.ai.client.KycAiClient;
import com.axion.ai.dto.OcrAnalysisResponse;
import com.axion.kyc.entity.KycDocument;
import com.axion.kyc.entity.KycVerification;
import com.axion.kyc.entity.VerificationAction;
import com.axion.kyc.entity.VerificationSignal;
import com.axion.kyc.entity.VerificationSignalResult;
import com.axion.kyc.entity.VerificationSignalType;
import com.axion.kyc.storage.DocumentStorageService;

@Component
public class OcrVerificationHandler
        implements VerificationHandler {

    private final KycAiClient kycAiClient;
    private final DocumentStorageService documentStorageService;
    public OcrVerificationHandler(
            KycAiClient kycAiClient,
            DocumentStorageService documentStorageService) {

        this.kycAiClient = kycAiClient;
        this.documentStorageService = documentStorageService;
    }

    @Override
    public VerificationAction getAction() {
        return VerificationAction.OCR;
    }

    @Override
    public VerificationSignal verify(
            KycVerification kyc) {

        KycDocument document =
                kyc.getDocuments()
                        .stream()
                        .findFirst()
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "No KYC document available."
                                ));

        /*
         * At this point we need the actual file bytes.
         *
         * The storage layer currently stores the file,
         * so next we will add a read() operation to it.
         */

        byte[] fileBytes =
        documentStorageService.read(
                document.getStorageReference()
        );

OcrAnalysisResponse result =
        kycAiClient.analyze(
                fileBytes,
                document.getOriginalFileName(),
                document.getContentType()
        );

VerificationSignalResult signalResult =
        result.documentReadable()
                ? VerificationSignalResult.PASS
                : VerificationSignalResult.UNCERTAIN;

return VerificationSignal.builder()
        .kycVerification(kyc)
        .signalType(
                VerificationSignalType.OCR
        )
        .result(signalResult)
        .confidence(
                result.confidenceScore()
        )
        .reason(
                result.reason()
        )
        .source("axion-kyc-ai")
        .sourceVersion("0.1")
        .build();
    }
}