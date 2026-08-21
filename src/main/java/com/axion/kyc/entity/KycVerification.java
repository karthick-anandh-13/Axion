package com.axion.kyc.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import com.axion.customer.entity.Customer;
import com.axion.authentication.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "kyc_verifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KycVerification {

        public User getUser() {
                return customer == null ? null : customer.getUser();
        }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "customer_id",
            nullable = false,
            unique = true
    )
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private KycStatus status = KycStatus.NOT_STARTED;

    @Column(length = 50)
    private String verificationReference;

    @Column(length = 1000)
    private String rejectionReason;

    private LocalDateTime submittedAt;

    private LocalDateTime verifiedAt;

    private LocalDateTime expiresAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {

        LocalDateTime now = LocalDateTime.now();

        createdAt = now;
        updatedAt = now;
    }


    @Builder.Default
    @OneToMany(
            mappedBy = "kycVerification",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<KycDocument> documents = new ArrayList<>();
    @PreUpdate
    public void preUpdate() {

        updatedAt = LocalDateTime.now();
    }
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private KycDecision decision;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private KycVerificationMethod verificationMethod;

    @Column
    private Double confidenceScore;

    @Column(length = 2000)
    private String decisionReason;
    @Builder.Default
    @OneToMany(
            mappedBy = "kycVerification",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<VerificationSignal> signals =
            new ArrayList<>();
}