package com.axion.loan.schedule;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.axion.loan.entity.Loan;
import com.axion.loan.entity.RepaymentStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "emi_installments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmiInstallment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;

    private Integer installmentNumber;

    private LocalDate dueDate;

    @Column(precision = 19, scale = 2)
    private BigDecimal emiAmount;

    @Column(precision = 19, scale = 2)
    private BigDecimal principalComponent;

    @Column(precision = 19, scale = 2)
    private BigDecimal interestComponent;

    @Column(precision = 19, scale = 2)
    private BigDecimal remainingBalance;

    @Enumerated(EnumType.STRING)
    private RepaymentStatus status;
}