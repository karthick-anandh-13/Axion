package com.axion.ledger.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.axion.ledger.entity.LedgerEntry;

public interface LedgerEntryRepository
        extends JpaRepository<LedgerEntry, UUID> {
}