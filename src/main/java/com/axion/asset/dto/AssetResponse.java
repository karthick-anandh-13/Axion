package com.axion.asset.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.axion.asset.entity.AssetOwnershipStatus;
import com.axion.asset.entity.AssetStatus;
import com.axion.asset.entity.AssetType;

public record AssetResponse(

        UUID id,

        UUID ownerId,

        AssetType type,

        String description,

        BigDecimal declaredValue,

        BigDecimal verifiedValue,

        AssetOwnershipStatus ownershipStatus,

        AssetStatus status,

        String country,

        String locationReference,

        LocalDateTime createdAt,

        LocalDateTime updatedAt,

        LocalDateTime verifiedAt

) {
}