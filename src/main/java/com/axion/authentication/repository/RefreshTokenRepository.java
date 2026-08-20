package com.axion.authentication.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.axion.authentication.entity.RefreshToken;

@Repository
public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUserId(UUID userId);

    void deleteByToken(String token);

    @Modifying
    @Query("""
            UPDATE RefreshToken r
            SET r.revoked = true
            WHERE r.user.id = :userId
            """)
    int revokeAllByUserId(UUID userId);
}