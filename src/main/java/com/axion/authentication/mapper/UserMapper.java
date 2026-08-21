package com.axion.authentication.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import com.axion.authentication.dto.RoleResponse;
import com.axion.authentication.dto.RegisterRequest;
import com.axion.authentication.dto.UserResponse;
import com.axion.authentication.entity.User;

public class UserMapper {

    private UserMapper() {
        // Prevent instantiation
    }

    /**
     * Converts RegisterRequest DTO to User entity.
     */
    public static User toEntity(RegisterRequest request) {

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());

        // Do NOT set:
        // user.setId(...)
        // user.setCreatedAt(...)
        // user.setUpdatedAt(...)
        // These are managed automatically.

        return user;
    }

    /**
     * Converts User entity to UserResponse DTO.
     */
    public static UserResponse toResponse(User user) {

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setStatus(user.getStatus());
        Set<RoleResponse> roles = user.getRoles()
        .stream()
        .map(role -> new RoleResponse(
                role.getId(),
                role.getName(),
                role.getDescription()
        ))
        .collect(Collectors.toSet());

        response.setRoles(roles);

        return response;
    }
}