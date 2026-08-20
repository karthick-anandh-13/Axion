package com.axion.ledger.controller;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.axion.ledger.entity.AccountType;
import com.axion.ledger.service.LedgerService;

@RestController
@RequestMapping("/api/v1/ledger")
public class LedgerController {

    private final LedgerService ledgerService;

    public LedgerController(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @PostMapping("/test")
    public ResponseEntity<UUID> createTestTransaction() {

        UUID id = ledgerService.createTransaction(
                "Test Transaction",
                AccountType.CASH,
                AccountType.PLATFORM_REVENUE,
                new BigDecimal("1000.00")
        );

        return ResponseEntity.ok(id);
    }
}