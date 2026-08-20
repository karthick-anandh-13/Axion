package com.axion.disbursement.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.axion.disbursement.dto.DisbursementResponse;
import com.axion.disbursement.service.DisbursementService;

@RestController
@RequestMapping("/api/v1/disbursements")
public class DisbursementController {

    private final DisbursementService disbursementService;

    public DisbursementController(
            DisbursementService disbursementService) {

        this.disbursementService = disbursementService;
    }

    @PostMapping("/{loanId}")
    public ResponseEntity<DisbursementResponse> disburse(
            @PathVariable UUID loanId) {

        return ResponseEntity.ok(
                disbursementService.disburseLoan(loanId));
    }
}