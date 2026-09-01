package com.axion.reporting.dto;

import java.time.LocalDate;

import org.springframework.lang.NonNull;

import jakarta.validation.constraints.NotNull;

public record ReportFilterRequest(

        @NotNull
        @NonNull
        LocalDate startDate,

        @NotNull
        @NonNull
        LocalDate endDate,

        @NotNull
        @NonNull
        String reportType

) {}