package com.axion.reporting.service.impl;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.axion.reporting.dto.DashboardReportResponse;
import com.axion.reporting.dto.LoanReportResponse;
import com.axion.reporting.dto.ReportFilterRequest;
import com.axion.reporting.dto.RevenueReportResponse;
import com.axion.reporting.service.ReportingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportingServiceImpl implements ReportingService {

    @Override
    @NonNull
    public DashboardReportResponse getDashboardReport(@NonNull UUID userId) {

        // Placeholder values until database aggregation is implemented
        return new DashboardReportResponse(
                8,
                5,
                2,
                1,
                new BigDecimal("750000"),
                new BigDecimal("215000"),
                new BigDecimal("535000"),
                new BigDecimal("18500"),
                4
        );
    }

    @Override
    @NonNull
    public LoanReportResponse getLoanReport(
            @NonNull ReportFilterRequest request) {

        return new LoanReportResponse(
                8,
                5,
                2,
                1,
                new BigDecimal("750000"),
                new BigDecimal("535000"),
                new BigDecimal("11.75"),
                new BigDecimal("18500")
        );
    }

    @Override
    @NonNull
    public RevenueReportResponse getRevenueReport(
            @NonNull ReportFilterRequest request) {

        return new RevenueReportResponse(
                new BigDecimal("96500"),
                new BigDecimal("21500"),
                new BigDecimal("75000"),
                42,
                3,
                new BigDecimal("12500")
        );
    }
}