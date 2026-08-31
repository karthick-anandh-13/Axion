package com.axion.ai.service;

import java.util.UUID;

import com.axion.ai.dto.CreditInsightResponse;

public interface CreditInsightService {

    CreditInsightResponse generateInsight(UUID userId);

}