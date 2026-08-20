package com.axion.kyc.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.axion.kyc.dto.KycResponse;
import com.axion.kyc.service.KycService;

@RestController
@RequestMapping("/api/kyc")
public class KycController {

    private final KycService kycService;

    public KycController(KycService kycService) {
        this.kycService = kycService;
    }

    /**
     * Creates an empty KYC verification record
     * for the authenticated customer.
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<KycResponse> createKyc(
            @AuthenticationPrincipal Jwt jwt) {

        UUID userId = getUserId(jwt);

        KycResponse response =
                kycService.createKyc(userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Returns the authenticated customer's KYC record.
     */
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<KycResponse> getMyKyc(
            @AuthenticationPrincipal Jwt jwt) {

        UUID userId = getUserId(jwt);

        KycResponse response =
                kycService.getMyKyc(userId);

        return ResponseEntity.ok(response);
    }

    /**
     * Submits the customer's KYC for verification.
     */
    @PostMapping("/me/submit")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<KycResponse> submitKyc(
            @AuthenticationPrincipal Jwt jwt) {

        UUID userId = getUserId(jwt);

        KycResponse response =
                kycService.submitKyc(userId);

        return ResponseEntity.ok(response);
    }

    private UUID getUserId(Jwt jwt) {
        return UUID.fromString(jwt.getSubject());
    }
}