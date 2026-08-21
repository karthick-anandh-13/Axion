package com.axion.loan.service;

import java.util.UUID;

import org.springframework.lang.NonNull;

public interface LoanService {

    @NonNull
    UUID createLoanFromOffer(@NonNull UUID loanOfferId);

}