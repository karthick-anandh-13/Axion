package com.axion.payment.dto;

import java.math.BigDecimal;

import com.axion.payment.entity.PaymentMethod;

import jakarta.validation.constraints.*;

public record CreatePaymentRequest(

        @NotNull
        BigDecimal amount,

        @NotNull
        PaymentMethod paymentMethod

) {
}