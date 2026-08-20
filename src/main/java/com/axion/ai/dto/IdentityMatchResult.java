package com.axion.ai.dto;

public record IdentityMatchResult(
        boolean matched,
        double confidence,
        double nameSimilarity,
        boolean dateOfBirthMatch,
        boolean documentNumberMatch,
        String reason
) {
}