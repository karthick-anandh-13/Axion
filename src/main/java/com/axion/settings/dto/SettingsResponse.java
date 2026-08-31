package com.axion.settings.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettingsResponse {

    private String fullName;
    private String username;
    private String email;
    private String phoneNumber;

    private boolean emailNotifications;
    private boolean smsNotifications;
    private boolean pushNotifications;

    private String theme;
    private String language;
}
