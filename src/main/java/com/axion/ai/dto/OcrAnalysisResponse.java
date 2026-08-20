package com.axion.ai.dto;

public record OcrAnalysisResponse(
        boolean documentReadable,
        double confidenceScore,
        String extractedName,
        String extractedDocumentNumber,
        String reason
) {
}