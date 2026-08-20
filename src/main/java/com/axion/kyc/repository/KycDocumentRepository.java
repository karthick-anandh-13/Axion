package com.axion.kyc.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axion.kyc.entity.KycDocument;
import com.axion.kyc.entity.KycDocumentType;

@Repository
public interface KycDocumentRepository
        extends JpaRepository<KycDocument, UUID> {

    List<KycDocument> findByKycVerificationId(
            UUID kycVerificationId
    );

    Optional<KycDocument> findByIdAndKycVerificationId(
            UUID id,
            UUID kycVerificationId
    );

    boolean existsByKycVerificationIdAndDocumentType(
            UUID kycVerificationId,
            KycDocumentType documentType
    );
}