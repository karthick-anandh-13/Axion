package com.axion.ai.dto;

public record DocumentAnalysisResponse(

        ExtractedField documentType,

        ExtractedField name,

        ExtractedField dateOfBirth,

        ExtractedField documentNumber,

        ExtractedField expiryDate,

        double overallConfidence,

        String rawText,

        String reason

) {
}