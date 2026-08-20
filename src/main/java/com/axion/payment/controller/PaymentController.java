package com.axion.payment.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.axion.payment.dto.CreatePaymentRequest;
import com.axion.payment.service.PaymentService;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(
            PaymentService paymentService) {

        this.paymentService = paymentService;
    }

    @PostMapping("/installments/{installmentId}")
    public ResponseEntity<UUID> payInstallment(
            @PathVariable UUID installmentId,
            @Valid @RequestBody CreatePaymentRequest request) {

        return ResponseEntity.ok(
                paymentService.payInstallment(
                        installmentId,
                        request
                )
        );
    }
}