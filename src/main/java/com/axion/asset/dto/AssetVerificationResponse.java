package com.axion.asset.dto;

import java.math.BigDecimal;
import java.util.List;

import com.axion.asset.entity.AssetStatus;

public record AssetVerificationResponse(

        AssetStatus status,

        BigDecimal verifiedValue,

        double confidence,

        List<String> verificationFactors,

        List<String> warnings

) {
}