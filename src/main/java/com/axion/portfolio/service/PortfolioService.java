package com.axion.portfolio.service;

import java.util.UUID;

import com.axion.portfolio.dto.PortfolioSummaryResponse;

public interface PortfolioService {

    PortfolioSummaryResponse getPortfolioSummary(UUID userId);

}