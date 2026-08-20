package com.axion.borrowing.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.axion.authentication.entity.User;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "borrower_financial_profiles",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_financial_profile_user",
                        columnNames = "borrower_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowerFinancialProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "borrower_id",
            nullable = false,
            unique = true
    )
    private User borrower;

    @Column(
            nullable = false,
            precision = 19,
            scale = 2
    )
    private BigDecimal monthlyIncome;

    @Column(
            nullable = false,
            precision = 19,
            scale = 2
    )
    private BigDecimal monthlyExpenses;

    @Column(
            nullable = false,
            precision = 19,
            scale = 2
    )
    private BigDecimal existingDebt;

    @Column(
            nullable = false,
            precision = 19,
            scale = 2
    )
    private BigDecimal monthlyDebtObligation;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 30
    )
    private EmploymentType employmentType;

    @Column(length = 100)
    private String employerName;

    @Column
    private Integer employmentDurationMonths;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 30
    )
    @Builder.Default
    private IncomeVerificationStatus incomeVerificationStatus =
            IncomeVerificationStatus.NOT_SUBMITTED;

    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {

        LocalDateTime now =
                LocalDateTime.now();

        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {

        updatedAt =
                LocalDateTime.now();
    }
}