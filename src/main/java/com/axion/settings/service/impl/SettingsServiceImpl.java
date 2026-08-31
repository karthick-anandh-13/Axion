package com.axion.settings.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.axion.authentication.entity.User;
import com.axion.authentication.service.UserService;
import com.axion.settings.dto.SettingsResponse;
import com.axion.settings.service.SettingsService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SettingsServiceImpl implements SettingsService {

    @NonNull
    private final UserService userService;

    @Override
    public SettingsResponse getSettings(UUID userId) {

        User user = userService.getUserById(userId);

        return new SettingsResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                false,
                false,
                true,
                "DARK",
                "ENGLISH"
        );
    }
}