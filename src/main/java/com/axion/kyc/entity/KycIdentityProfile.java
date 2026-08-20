package com.axion.kyc.entity;

import java.time.LocalDate;
import java.util.UUID;

import com.axion.authentication.entity.User;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "kyc_identity_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KycIdentityProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true
    )
    private User user;

    @Column(length = 100)
    private String legalName;

    private LocalDate dateOfBirth;

    @Column(length = 50)
    private String governmentDocumentNumber;

    @Column(length = 50)
    private String nationality;

    @Column(nullable = false)
    @Builder.Default
    private boolean identityVerified = false;
}