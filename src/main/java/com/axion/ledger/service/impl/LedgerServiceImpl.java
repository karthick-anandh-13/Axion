package com.axion.ledger.service.impl;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axion.ledger.entity.AccountType;
import com.axion.ledger.entity.EntryType;
import com.axion.ledger.entity.LedgerEntry;
import com.axion.ledger.entity.LedgerTransaction;
import com.axion.ledger.repository.LedgerEntryRepository;
import com.axion.ledger.repository.LedgerTransactionRepository;
import com.axion.ledger.service.LedgerService;

@Service
@Transactional
public class LedgerServiceImpl implements LedgerService {

    private final LedgerTransactionRepository transactionRepository;
    private final LedgerEntryRepository entryRepository;

    public LedgerServiceImpl(
            LedgerTransactionRepository transactionRepository,
            LedgerEntryRepository entryRepository) {

        this.transactionRepository = transactionRepository;
        this.entryRepository = entryRepository;
    }

    @Override
    public UUID createTransaction(
            String description,
            AccountType debitAccount,
            AccountType creditAccount,
            BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero");
        }

        LedgerTransaction transaction = LedgerTransaction.builder()
                .referenceNumber(generateReference())
                .description(description)
                .build();

        transaction = transactionRepository.save(transaction);

        LedgerEntry debit = LedgerEntry.builder()
                .transaction(transaction)
                .account(debitAccount)
                .entryType(EntryType.DEBIT)
                .amount(amount)
                .build();

        LedgerEntry credit = LedgerEntry.builder()
                .transaction(transaction)
                .account(creditAccount)
                .entryType(EntryType.CREDIT)
                .amount(amount)
                .build();

        entryRepository.save(debit);
        entryRepository.save(credit);

        return transaction.getId();
    }

    private String generateReference() {

        return "AXN-" +
                System.currentTimeMillis() +
                "-" +
                UUID.randomUUID().toString().substring(0, 8);
    }
}