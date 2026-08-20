package com.axion.notification.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.axion.notification.entity.Notification;
import com.axion.notification.repository.NotificationRepository;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationRepository repository;

    public NotificationController(NotificationRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getNotifications(
            @PathVariable UUID userId) {

        return ResponseEntity.ok(
                repository.findByUserIdOrderBySentAtDesc(userId)
        );
    }
}