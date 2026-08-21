package com.axion.disbursement.service;

import java.util.UUID;

import org.springframework.lang.NonNull;

import com.axion.disbursement.dto.DisbursementResponse;

public interface DisbursementService {

    @NonNull
    DisbursementResponse disburseLoan(
            @NonNull UUID loanId
    );
}