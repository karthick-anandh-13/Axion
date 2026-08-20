package com.axion.authentication.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import com.axion.authentication.entity.UserStatus;
import com.axion.authentication.exception.AccountDisabledException;
import com.axion.authentication.exception.InvalidCredentialsException;
import com.axion.authentication.security.JwtTokenService;
import com.axion.authentication.dto.LoginRequest;
import com.axion.authentication.dto.LoginResponse;
import com.axion.authentication.dto.RefreshTokenRequest;
import com.axion.authentication.dto.RegisterRequest;
import com.axion.authentication.dto.UserResponse;
import com.axion.authentication.entity.RefreshToken;
import com.axion.authentication.entity.User;
import com.axion.authentication.mapper.UserMapper;
import com.axion.authentication.service.AuthService;
import com.axion.authentication.service.RefreshTokenService;
import com.axion.authentication.service.UserService;
import com.axion.authentication.security.JwtProperties;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final RefreshTokenService refreshTokenService;
    private final JwtProperties jwtProperties;
    
    public AuthServiceImpl(
            UserService userService,
            PasswordEncoder passwordEncoder,
            JwtTokenService jwtTokenService,
            RefreshTokenService refreshTokenService,
            JwtProperties jwtProperties) {

        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.refreshTokenService = refreshTokenService;
        this.jwtProperties = jwtProperties; 
    }

    @Override
    @Transactional
    public UserResponse register(RegisterRequest request) {

        User user = UserMapper.toEntity(request);

        User savedUser = userService.createUser(user);

        return UserMapper.toResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {

        User user = findUserForAuthentication(request.getUsernameOrEmail());

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new AccountDisabledException(
                    "Your account is not active."
            );
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new InvalidCredentialsException(
                    "Invalid username/email or password."
            );
        }

        UserResponse userResponse = UserMapper.toResponse(user);

        String accessToken = jwtTokenService.generateAccessToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new LoginResponse(
                accessToken,
                refreshToken.getToken(),
                "Bearer",
                jwtProperties.getAccessTokenExpiration(),
                userResponse
        );
    }

    private User findUserForAuthentication(String usernameOrEmail) {

        Optional<User> user;

        if (usernameOrEmail.contains("@")) {
            user = userService.findByEmail(usernameOrEmail);
        } else {
            user = userService.findByUsername(usernameOrEmail);
        }

        User authenticatedUser = user.orElseThrow(() ->
                new InvalidCredentialsException(
                        "Invalid username/email or password."
                )
        );
        return authenticatedUser;
    }
    @Override
@Transactional
public LoginResponse refreshAccessToken(RefreshTokenRequest request) {

    RefreshToken newRefreshToken =
            refreshTokenService.rotateRefreshToken(
                    request.getRefreshToken()
            );

    User user = newRefreshToken.getUser();

    String accessToken =
            jwtTokenService.generateAccessToken(user);

    UserResponse userResponse =
            UserMapper.toResponse(user);

    return new LoginResponse(
            accessToken,
            newRefreshToken.getToken(),
            "Bearer",
            jwtProperties.getAccessTokenExpiration(),
            userResponse
    );
    }

}