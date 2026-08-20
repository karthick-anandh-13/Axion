package com.axion.lending.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lending_capacity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LendingCapacity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "partner_id", nullable = false, unique = true)
    private LendingPartner partner;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal totalCapital;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal committedCapital;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal availableCapital;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal minimumApr;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal maximumApr;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void updateTime() {
        updatedAt = LocalDateTime.now();
    }
}