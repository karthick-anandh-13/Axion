package com.axion.reporting.controller;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.axion.reporting.dto.DashboardReportResponse;
import com.axion.reporting.dto.LoanReportResponse;
import com.axion.reporting.dto.ReportFilterRequest;
import com.axion.reporting.dto.RevenueReportResponse;
import com.axion.reporting.service.ReportingService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reporting")
@RequiredArgsConstructor
public class ReportingController {

    @NonNull
    private final ReportingService reportingService;

    /**
     * Dashboard analytics report
     * GET /api/v1/reporting/dashboard?userId=<UUID>
     */
    @GetMapping("/dashboard")
    @NonNull
    public DashboardReportResponse getDashboardReport(
            @RequestParam @NotNull UUID userId) {

        return reportingService.getDashboardReport(userId);
    }

    /**
     * Loan report
     * POST /api/v1/reporting/loans
     */
    @PostMapping("/loans")
    @NonNull
    public LoanReportResponse getLoanReport(
            @Valid @RequestBody @NotNull ReportFilterRequest request) {

        return reportingService.getLoanReport(request);
    }

    /**
     * Revenue report
     * POST /api/v1/reporting/revenue
     */
    @PostMapping("/revenue")
    @NonNull
    public RevenueReportResponse getRevenueReport(
            @Valid @RequestBody @NotNull ReportFilterRequest request) {

        return reportingService.getRevenueReport(request);
    }
}