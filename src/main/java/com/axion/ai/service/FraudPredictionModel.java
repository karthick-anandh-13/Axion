package com.axion.ai.service;

import com.axion.ai.dto.FraudRiskResult;

public interface FraudPredictionModel {
    FraudRiskResult predict(FraudPredictionInput input);
}
