package com.axion.asset.verification;
import java.math.BigDecimal;
import java.util.List;
import com.axion.asset.entity.AssetStatus;
public record AssetVerificationResult(AssetStatus status, BigDecimal verifiedValue, double confidence, List<String> verificationFactors, List<String> warnings) {}
