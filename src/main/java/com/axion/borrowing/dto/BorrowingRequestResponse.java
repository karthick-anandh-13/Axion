package com.axion.borrowing.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.axion.borrowing.entity.BorrowingPurpose;
import com.axion.borrowing.entity.BorrowingRequestStatus;

public record BorrowingRequestResponse(

        UUID id,

        UUID borrowerId,

        BorrowingPurpose purpose,

        String purposeDescription,

        BigDecimal requestedAmount,

        Integer requestedTenureMonths,

        BigDecimal maximumAcceptableApr,

        BorrowingRequestStatus status,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}