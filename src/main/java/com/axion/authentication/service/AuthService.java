package com.axion.authentication.service;

import com.axion.authentication.dto.LoginRequest;
import com.axion.authentication.dto.LoginResponse;
import com.axion.authentication.dto.RegisterRequest;
import com.axion.authentication.dto.UserResponse;

public interface AuthService {

    UserResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}