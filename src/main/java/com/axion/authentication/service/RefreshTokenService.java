package com.axion.authentication.service;

import com.axion.authentication.entity.RefreshToken;
import com.axion.authentication.entity.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user);

    RefreshToken validateRefreshToken(String token);

    void revokeToken(String token);

    RefreshToken rotateRefreshToken(String token);
}