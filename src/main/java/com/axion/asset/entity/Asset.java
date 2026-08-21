package com.axion.asset.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.axion.authentication.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "assets")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Asset {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "owner_id", nullable = false) private User owner;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 40) private AssetType type;
    @Column(nullable = false, length = 255) private String description;
    @Column(nullable = false, precision = 19, scale = 2) private BigDecimal declaredValue;
    @Column(precision = 19, scale = 2) private BigDecimal verifiedValue;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 30) @Builder.Default private AssetOwnershipStatus ownershipStatus = AssetOwnershipStatus.UNKNOWN;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 30) @Builder.Default private AssetStatus status = AssetStatus.DECLARED;
    @Column(length = 100) private String country;
    @Column(length = 255) private String locationReference;
    @Column(nullable = false) private LocalDateTime createdAt;
    @Column(nullable = false) private LocalDateTime updatedAt;
    private LocalDateTime verifiedAt;
    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = createdAt; }
    @PreUpdate protected void onUpdate() { updatedAt = LocalDateTime.now(); }
}
