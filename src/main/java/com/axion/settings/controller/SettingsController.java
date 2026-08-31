package com.axion.settings.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axion.settings.dto.SettingsResponse;
import com.axion.settings.service.SettingsService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/settings")
@RequiredArgsConstructor
public class SettingsController {

    @NonNull
    private final SettingsService settingsService;

    @GetMapping
    public SettingsResponse getSettings(
            @RequestHeader("X-User-Id") UUID userId) {

        return settingsService.getSettings(userId);
    }
}