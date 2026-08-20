package com.axion.offer.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.axion.offer.entity.LoanOfferStatus;

public record LoanOfferResponse(

        UUID offerId,

        UUID lendingPartnerId,

        String organizationName,

        BigDecimal principal,

        BigDecimal apr,

        Integer tenureMonths,

        BigDecimal monthlyEmi,

        BigDecimal platformFee,

        LoanOfferStatus status,

        LocalDateTime expiresAt

) {
}