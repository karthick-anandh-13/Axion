package com.axion.repayment.controller;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.axion.repayment.dto.RepaymentSummaryResponse;
import com.axion.repayment.service.RepaymentService;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/repayment")
@RequiredArgsConstructor
public class RepaymentController {

    @NonNull
    private final RepaymentService repaymentService;

    @GetMapping("/summary")
    public RepaymentSummaryResponse getRepaymentSummary(
            @RequestParam @NotNull UUID userId) {

        return repaymentService.getRepaymentSummary(userId);
    }
}