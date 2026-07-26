package com.axion.authentication.service;

import java.util.UUID;

import com.axion.authentication.entity.User;

public interface UserService {

    User createUser(User user);

    User getUserById(UUID id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}