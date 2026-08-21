package com.axion.lending.entity;
import java.time.LocalDateTime;
import java.util.UUID;
import com.axion.authentication.entity.User;
import jakarta.persistence.*;
import lombok.*;
@Entity @Table(name = "lending_partners") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LendingPartner {
 @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
 @OneToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "user_id", nullable = false, unique = true) private User user;
 @Column(nullable = false, length = 150) private String organizationName;
 @Enumerated(EnumType.STRING) @Column(nullable = false, length = 30) @Builder.Default private LendingPartnerStatus status = LendingPartnerStatus.PENDING;
 @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) @Builder.Default private RiskPreference riskPreference = RiskPreference.MEDIUM;
 @Column(nullable = false) private LocalDateTime createdAt;
 @PrePersist public void onCreate() { createdAt = LocalDateTime.now(); }
}
