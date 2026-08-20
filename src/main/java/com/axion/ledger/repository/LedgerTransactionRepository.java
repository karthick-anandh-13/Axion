package com.axion.ledger.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.axion.ledger.entity.LedgerTransaction;

public interface LedgerTransactionRepository
        extends JpaRepository<LedgerTransaction, UUID> {
}