package com.axion.kyc.service;

import com.axion.kyc.entity.DocumentAnalysisStatus;

public record DocumentAnalysisResult(
        DocumentAnalysisStatus status,
        boolean documentReadable,
        boolean documentTypeValid,
        boolean documentExpired,
        double confidenceScore,
        String extractedName,
        String extractedDocumentNumber,
        String reason
) {
}