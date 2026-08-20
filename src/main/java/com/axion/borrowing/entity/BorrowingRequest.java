package com.axion.borrowing.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.axion.authentication.entity.User;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "borrowing_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "borrower_id",
            nullable = false
    )
    private User borrower;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 40
    )
    private BorrowingPurpose purpose;

    @Column(length = 1000)
    private String purposeDescription;

    @Column(
            nullable = false,
            precision = 19,
            scale = 2
    )
    private BigDecimal requestedAmount;

    @Column(nullable = false)
    private Integer requestedTenureMonths;

    @Column(
            precision = 5,
            scale = 2
    )
    private BigDecimal maximumAcceptableApr;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 30
    )
    @Builder.Default
    private BorrowingRequestStatus status =
            BorrowingRequestStatus.DRAFT;

    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {

        updatedAt = LocalDateTime.now();
    }
}