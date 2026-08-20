package com.axion.notification.service;

import java.util.UUID;

import com.axion.notification.entity.NotificationType;

public interface NotificationService {

    void sendNotification(
            UUID userId,
            NotificationType type,
            String title,
            String message
    );

}