package com.axion.ai.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axion.ai.dto.CreditInsightResponse;
import com.axion.ai.service.CreditInsightService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiController {

    @NonNull
    private final CreditInsightService creditInsightService;

    @GetMapping("/credit-insight")
    public CreditInsightResponse getCreditInsight(
            @RequestHeader("X-User-Id") UUID userId) {

        return creditInsightService.generateInsight(userId);
    }
}