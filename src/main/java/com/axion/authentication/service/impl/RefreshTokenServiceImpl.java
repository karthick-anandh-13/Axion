package com.axion.authentication.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axion.authentication.entity.RefreshToken;
import com.axion.authentication.entity.User;
import com.axion.authentication.repository.RefreshTokenRepository;
import com.axion.authentication.security.JwtProperties;
import com.axion.authentication.service.RefreshTokenService;
import com.axion.authentication.exception.RefreshTokenReuseException;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;

    public RefreshTokenServiceImpl(
            RefreshTokenRepository refreshTokenRepository,
            JwtProperties jwtProperties) {

        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtProperties = jwtProperties;
    }

    @Override
    @Transactional
    public RefreshToken createRefreshToken(User user) {

        LocalDateTime expiresAt =
                LocalDateTime.now().plusSeconds(
                        jwtProperties.getRefreshTokenExpiration()
                );

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiresAt(expiresAt)
                .revoked(false)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    @Transactional
    public void revokeToken(String token) {

        refreshTokenRepository.findByToken(token)
                .ifPresent(refreshToken -> {
                    refreshToken.setRevoked(true);
                    refreshTokenRepository.save(refreshToken);
                });
    }
    @Override
    @Transactional
    public RefreshToken rotateRefreshToken(String token) {

        RefreshToken oldToken = validateRefreshToken(token);

        User user = oldToken.getUser();

        oldToken.setRevoked(true);
        refreshTokenRepository.save(oldToken);

        return createRefreshToken(user);
    }
    @Override
@Transactional
public RefreshToken validateRefreshToken(String token) {

    RefreshToken refreshToken =
            refreshTokenRepository.findByToken(token)
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Invalid refresh token."
                            ));

    if (refreshToken.isRevoked()) {

        refreshTokenRepository.revokeAllByUserId(
                refreshToken.getUser().getId()
        );

        throw new RefreshTokenReuseException(
                "Refresh token reuse detected."
        );
    }

    if (refreshToken.getExpiresAt()
            .isBefore(LocalDateTime.now())) {

        throw new IllegalArgumentException(
                "Refresh token has expired."
        );
    }

    return refreshToken;
}
}