package com.axion.notification.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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

    // Existing endpoint (keep this)
    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsByUserId(
            @PathVariable UUID userId) {

        return ResponseEntity.ok(
                repository.findByUserIdOrderBySentAtDesc(userId)
        );
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getCurrentUserNotifications(
            @AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        return ResponseEntity.ok(
                repository.findByUserIdOrderBySentAtDesc(userId)
        );
    }

    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable UUID notificationId,
                                           @AuthenticationPrincipal Jwt jwt) {
        Notification notification = repository.findById(notificationId)
                .filter(item -> item.getUser().getId().equals(UUID.fromString(jwt.getSubject())))
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Notification not found"));
        notification.setStatus(com.axion.notification.entity.NotificationStatus.READ);
        repository.save(notification);
        return ResponseEntity.noContent().build();
    }
}
