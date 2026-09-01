package com.axion.reporting.repository;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.axion.reporting.dto.DashboardReportResponse;
import com.axion.reporting.dto.LoanReportResponse;
import com.axion.reporting.dto.ReportFilterRequest;
import com.axion.reporting.dto.RevenueReportResponse;

@Repository
public interface ReportingRepository {

    @NonNull
    DashboardReportResponse getDashboardReport(@NonNull UUID userId);

    @NonNull
    LoanReportResponse getLoanReport(@NonNull ReportFilterRequest request);

    @NonNull
    RevenueReportResponse getRevenueReport(@NonNull ReportFilterRequest request);
}