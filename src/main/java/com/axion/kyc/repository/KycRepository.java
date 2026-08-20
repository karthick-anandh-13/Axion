package com.axion.kyc.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axion.kyc.entity.KycVerification;

@Repository
public interface KycRepository
        extends JpaRepository<KycVerification, UUID> {

    Optional<KycVerification> findByCustomerId(UUID customerId);

    boolean existsByCustomerId(UUID customerId);
}