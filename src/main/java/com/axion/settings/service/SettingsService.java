package com.axion.settings.service;

import java.util.UUID;

import com.axion.settings.dto.SettingsResponse;

public interface SettingsService {

    SettingsResponse getSettings(UUID userId);

}
