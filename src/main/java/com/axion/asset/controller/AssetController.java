package com.axion.asset.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.axion.asset.dto.AssetResponse;
import com.axion.asset.dto.CreateAssetRequest;
import com.axion.asset.service.AssetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/assets")
@Validated
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AssetResponse> createAsset(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateAssetRequest request) {

        UUID ownerId = UUID.fromString(jwt.getSubject());

        AssetResponse response = assetService.createAsset(ownerId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<AssetResponse>> getMyAssets(
            @AuthenticationPrincipal Jwt jwt) {

        UUID ownerId = UUID.fromString(jwt.getSubject());

        return ResponseEntity.ok(assetService.getOwnerAssets(ownerId));
    }

    @GetMapping("/{assetId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AssetResponse> getAsset(
            @PathVariable UUID assetId) {

        return ResponseEntity.ok(assetService.getAsset(assetId));
    }
}