package com.axion.lending.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.axion.lending.dto.CreateLendingPartnerRequest;
import com.axion.lending.service.LendingPartnerService;

@RestController
@RequestMapping("/api/v1/lending")
public class LendingPartnerController {

    private final LendingPartnerService lendingPartnerService;

    public LendingPartnerController(
            LendingPartnerService lendingPartnerService) {

        this.lendingPartnerService = lendingPartnerService;
    }

    @PostMapping("/partners")
    public ResponseEntity<String> registerPartner(
            @RequestParam UUID userId,
            @Valid @RequestBody CreateLendingPartnerRequest request) {

        lendingPartnerService.registerPartner(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Lending partner registered successfully");
    }
}