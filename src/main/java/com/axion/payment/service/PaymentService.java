package com.axion.payment.service;

import java.util.UUID;

import org.springframework.lang.NonNull;

import com.axion.payment.dto.CreatePaymentRequest;

public interface PaymentService {

    @NonNull
    UUID payInstallment(
            @NonNull UUID installmentId,
            @NonNull CreatePaymentRequest request
    );

}