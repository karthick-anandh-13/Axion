package com.axion.asset.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "asset_documents",
        indexes = {
                @Index(
                        name = "idx_asset_document_asset",
                        columnList = "asset_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "asset_id",
            nullable = false
    )
    private Asset asset;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 40
    )
    private AssetDocumentType documentType;

    @Column(
            nullable = false,
            length = 500
    )
    private String storageReference;

    @Column(
            nullable = false,
            length = 128
    )
    private String fileHash;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 30
    )
    @Builder.Default
    private AssetDocumentStatus status =
            AssetDocumentStatus.UPLOADED;

    @Column(length = 100)
    private String mimeType;

    @Column
    private Long fileSize;

    @Column
    private LocalDateTime uploadedAt;

    @Column
    private LocalDateTime verifiedAt;

    @PrePersist
    protected void onCreate() {

        uploadedAt =
                LocalDateTime.now();
    }
}