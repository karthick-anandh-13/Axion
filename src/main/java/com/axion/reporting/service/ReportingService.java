package com.axion.reporting.service;

import java.util.UUID;

import org.springframework.lang.NonNull;

import com.axion.reporting.dto.DashboardReportResponse;
import com.axion.reporting.dto.LoanReportResponse;
import com.axion.reporting.dto.ReportFilterRequest;
import com.axion.reporting.dto.RevenueReportResponse;

public interface ReportingService {

    @NonNull
    DashboardReportResponse getDashboardReport(@NonNull UUID userId);

    @NonNull
    LoanReportResponse getLoanReport(@NonNull ReportFilterRequest request);

    @NonNull
    RevenueReportResponse getRevenueReport(@NonNull ReportFilterRequest request);

}