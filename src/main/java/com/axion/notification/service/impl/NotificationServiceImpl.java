package com.axion.notification.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axion.authentication.entity.User;
import com.axion.authentication.repository.UserRepository;
import com.axion.notification.entity.Notification;
import com.axion.notification.entity.NotificationStatus;
import com.axion.notification.entity.NotificationType;
import com.axion.notification.repository.NotificationRepository;
import com.axion.notification.service.NotificationService;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(
            UserRepository userRepository,
            NotificationRepository notificationRepository) {

        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void sendNotification(
            UUID userId,
            NotificationType type,
            String title,
            String message) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));

        Notification notification = Notification.builder()
                .user(user)
                .type(type)
                .title(title)
                .message(message)
                .status(NotificationStatus.SENT)
                .sentAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);
    }
}