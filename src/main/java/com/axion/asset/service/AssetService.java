package com.axion.asset.service;

import java.util.List;
import java.util.UUID;

import com.axion.asset.dto.AssetResponse;
import com.axion.asset.dto.CreateAssetRequest;

public interface AssetService {

    AssetResponse createAsset(
            UUID ownerId,
            CreateAssetRequest request
    );

    AssetResponse getAsset(
            UUID assetId
    );

    List<AssetResponse> getOwnerAssets(
            UUID ownerId
    );
}