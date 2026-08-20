package com.axion.ledger.service;

import java.math.BigDecimal;
import java.util.UUID;

import com.axion.ledger.entity.AccountType;

public interface LedgerService {

    UUID createTransaction(
            String description,
            AccountType debitAccount,
            AccountType creditAccount,
            BigDecimal amount
    );

}