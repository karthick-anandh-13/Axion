package com.axion.offer.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.axion.offer.dto.LoanOfferResponse;
import com.axion.offer.service.LoanOfferService;

@RestController
@RequestMapping("/api/v1/offers")
public class LoanOfferController {

    private final LoanOfferService loanOfferService;

    public LoanOfferController(LoanOfferService loanOfferService) {
        this.loanOfferService = loanOfferService;
    }

    @PostMapping("/{borrowingRequestId}/generate")
    public ResponseEntity<List<LoanOfferResponse>> generateOffers(
            @PathVariable UUID borrowingRequestId) {

        return ResponseEntity.ok(
                loanOfferService.generateOffers(borrowingRequestId)
        );
    }

    @GetMapping("/{borrowingRequestId}")
    public ResponseEntity<List<LoanOfferResponse>> getOffers(
            @PathVariable UUID borrowingRequestId) {

        return ResponseEntity.ok(
                loanOfferService.getOffers(borrowingRequestId)
        );
    }
}