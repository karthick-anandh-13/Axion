package com.axion.disbursement.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.axion.disbursement.entity.DisbursementStatus;

public record DisbursementResponse(

        UUID id,

        UUID loanId,

        BigDecimal amount,

        String transactionReference,

        DisbursementStatus status,

        LocalDateTime processedAt

) {
}