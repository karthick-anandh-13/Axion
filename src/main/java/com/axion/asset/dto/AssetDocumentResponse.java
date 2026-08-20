package com.axion.asset.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.axion.asset.entity.AssetDocumentStatus;
import com.axion.asset.entity.AssetDocumentType;

public record AssetDocumentResponse(

        UUID id,

        UUID assetId,

        AssetDocumentType documentType,

        AssetDocumentStatus status,

        String mimeType,

        Long fileSize,

        LocalDateTime uploadedAt,

        LocalDateTime verifiedAt

) {
}