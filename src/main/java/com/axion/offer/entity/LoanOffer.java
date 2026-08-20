package com.axion.offer.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.axion.borrowing.entity.BorrowingRequest;
import com.axion.lending.entity.LendingPartner;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "loan_offers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private BorrowingRequest borrowingRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private LendingPartner lendingPartner;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal principal;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal apr;

    @Column(nullable = false)
    private Integer tenureMonths;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal monthlyEmi;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal platformFee;

    @Enumerated(EnumType.STRING)
    private LoanOfferStatus status;

    private LocalDateTime expiresAt;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();

        expiresAt = createdAt.plusDays(7);

        if (status == null) {
            status = LoanOfferStatus.GENERATED;
        }
    }
}