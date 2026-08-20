package com.axion.loan.service;

import java.util.UUID;

public interface LoanService {

    UUID createLoanFromOffer(UUID loanOfferId);

}