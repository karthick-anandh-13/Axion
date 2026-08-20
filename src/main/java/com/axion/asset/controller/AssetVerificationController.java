package com.axion.asset.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.axion.asset.dto.AssetVerificationResponse;
import com.axion.asset.verification.AssetVerificationResult;
import com.axion.asset.verification.AssetVerificationService;

@RestController
@RequestMapping("/api/v1/assets")
public class AssetVerificationController {

    private final AssetVerificationService verificationService;

    public AssetVerificationController(
            AssetVerificationService verificationService) {

        this.verificationService =
                verificationService;
    }

    @PostMapping("/{assetId}/verify")
    public ResponseEntity<AssetVerificationResponse>
    verifyAsset(
            @PathVariable UUID assetId) {

        AssetVerificationResult result =
                verificationService.verifyAsset(
                        assetId
                );

        AssetVerificationResponse response =
                new AssetVerificationResponse(
                        result.status(),
                        result.verifiedValue(),
                        result.confidence(),
                        result.verificationFactors(),
                        result.warnings()
                );

        return ResponseEntity.ok(response);
    }
}