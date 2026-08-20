package com.axion.disbursement.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.axion.loan.entity.Loan;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "disbursements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Disbursement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", nullable = false, unique = true)
    private Loan loan;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, unique = true)
    private String transactionReference;

    @Enumerated(EnumType.STRING)
    private DisbursementStatus status;

    private LocalDateTime processedAt;

    @PrePersist
    public void onCreate() {

        processedAt = LocalDateTime.now();

        if (status == null) {
            status = DisbursementStatus.PENDING;
        }
    }
}