package com.axion.portfolio.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axion.portfolio.dto.PortfolioSummaryResponse;
import com.axion.portfolio.service.PortfolioService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    @NonNull
    private final PortfolioService portfolioService;

    @GetMapping("/summary")
    public PortfolioSummaryResponse getSummary(
            @RequestHeader("X-User-Id") UUID userId) {

        return portfolioService.getPortfolioSummary(userId);
    }
}