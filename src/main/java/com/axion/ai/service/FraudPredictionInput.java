package com.axion.ai.service;

public record FraudPredictionInput(double confidence, boolean duplicateDocument) {
}
