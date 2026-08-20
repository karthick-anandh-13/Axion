package com.axion.asset.dto;

import java.math.BigDecimal;

import com.axion.asset.entity.AssetOwnershipStatus;
import com.axion.asset.entity.AssetType;

import jakarta.validation.constraints.*;

public record CreateAssetRequest(

        @NotNull(message = "Asset type is required")
        AssetType type,

        @NotBlank(message = "Asset description is required")
        @Size(
                max = 255,
                message = "Description cannot exceed 255 characters"
        )
        String description,

        @NotNull(message = "Declared value is required")
        @DecimalMin(
                value = "0.0",
                message = "Declared value cannot be negative"
        )
        BigDecimal declaredValue,

        @NotNull(message = "Ownership status is required")
        AssetOwnershipStatus ownershipStatus,

        @Size(
                max = 100,
                message = "Country cannot exceed 100 characters"
        )
        String country,

        @Size(
                max = 255,
                message = "Location reference cannot exceed 255 characters"
        )
        String locationReference

) {
}