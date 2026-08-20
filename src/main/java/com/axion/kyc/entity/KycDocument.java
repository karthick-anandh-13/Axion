package com.axion.kyc.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "kyc_documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KycDocument {

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
    @Column(nullable = false, length = 30)
    private KycDocumentType documentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private KycDocumentStatus status =
            KycDocumentStatus.UPLOADED;

    /**
     * Reference to the object-storage location.
     *
     * Do not store the actual document binary
     * inside PostgreSQL.
     */
    @Column(nullable = false, length = 500)
    private String storageReference;

    @Column(length = 100)
    private String originalFileName;

    @Column(length = 100)
    private String contentType;

    private Long fileSize;

    /**
     * SHA-256 or equivalent integrity hash.
     */
    @Column(length = 128)
    private String documentHash;

    private LocalDate issuedDate;

    private LocalDate expiryDate;

    @Column(length = 1000)
    private String rejectionReason;

    private LocalDateTime verifiedAt;

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

    @PreUpdate
    public void preUpdate() {

        updatedAt = LocalDateTime.now();
    }
}