package com.axion.kyc.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "verification_signals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerificationSignal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "kyc_verification_id",
            nullable = false
    )
    private KycVerification kycVerification;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private VerificationSignalType signalType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private VerificationSignalResult result;

    /**
     * Confidence produced by the verification method.
     * Range: 0.0 - 1.0
     */
    @Column(nullable = false)
    private Double confidence;

    /**
     * Human-readable explanation of the signal.
     */
    @Column(length = 2000)
    private String reason;

    /**
     * Name/version of the algorithm or model
     * that generated this signal.
     */
    @Column(length = 100)
    private String source;

    /**
     * Allows us to reproduce which model/rule version
     * produced a decision.
     */
    @Column(length = 50)
    private String sourceVersion;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}