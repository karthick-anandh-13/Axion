package com.axion.payment.service;

import java.util.UUID;

import com.axion.payment.dto.CreatePaymentRequest;

public interface PaymentService {

    UUID payInstallment(
            UUID installmentId,
            CreatePaymentRequest request
    );

}