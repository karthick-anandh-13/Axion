package com.axion.ai.dto;

public record FaceMatchResult(
        boolean faceDetectedInDocument,
        boolean faceDetectedInSelfie,
        boolean matched,
        double similarity,
        double confidence,
        String reason
) {
}