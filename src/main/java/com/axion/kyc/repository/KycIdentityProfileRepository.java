package com.axion.kyc.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axion.kyc.entity.KycIdentityProfile;

@Repository
public interface KycIdentityProfileRepository
        extends JpaRepository<KycIdentityProfile, UUID> {

    Optional<KycIdentityProfile> findByUserId(
            UUID userId
    );
}