package com.axion.matching.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.axion.borrowing.entity.BorrowingRequest;
import com.axion.lending.entity.LendingPartner;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lender_matches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LenderMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private BorrowingRequest borrowingRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private LendingPartner lendingPartner;

    @Column(nullable = false)
    private Double matchingScore;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal offeredApr;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = MatchStatus.GENERATED;
        }
    }
}