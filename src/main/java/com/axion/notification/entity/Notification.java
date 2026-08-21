package com.axion.notification.entity;
import java.time.LocalDateTime;
import java.util.UUID;
import com.axion.authentication.entity.User;
import jakarta.persistence.*;
import lombok.*;
@Entity @Table(name = "notifications") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Notification {
 @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
 @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false) private User user;
 @Enumerated(EnumType.STRING) private NotificationType type;
 @Enumerated(EnumType.STRING) private NotificationStatus status;
 @Column(nullable = false, length = 150) private String title;
 @Column(nullable = false, length = 1000) private String message;
 private LocalDateTime sentAt;
 @PrePersist public void onCreate() { if (status == null) status = NotificationStatus.PENDING; }
}
