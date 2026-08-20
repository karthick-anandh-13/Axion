package com.axion.kyc.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axion.kyc.entity.VerificationSignal;
import com.axion.kyc.entity.VerificationSignalType;

@Repository
public interface VerificationSignalRepository
        extends JpaRepository<VerificationSignal, UUID> {

    List<VerificationSignal>
    findByKycVerificationId(UUID kycVerificationId);

    List<VerificationSignal>
    findByKycVerificationIdAndSignalType(
            UUID kycVerificationId,
            VerificationSignalType signalType
    );
}