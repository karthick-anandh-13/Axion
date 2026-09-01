package com.axion.repayment.service;

import java.util.UUID;

import org.springframework.lang.NonNull;

import com.axion.repayment.dto.RepaymentSummaryResponse;

public interface RepaymentService {

    @NonNull
    RepaymentSummaryResponse getRepaymentSummary(@NonNull UUID userId);

}