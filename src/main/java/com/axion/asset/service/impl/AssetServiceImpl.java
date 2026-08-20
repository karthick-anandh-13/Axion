package com.axion.asset.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axion.asset.dto.AssetResponse;
import com.axion.asset.dto.CreateAssetRequest;
import com.axion.asset.entity.Asset;
import com.axion.asset.repository.AssetRepository;
import com.axion.asset.service.AssetService;
import com.axion.authentication.entity.User;
import com.axion.authentication.repository.UserRepository;

@Service
@Transactional
public class AssetServiceImpl
        implements AssetService {

    private final AssetRepository assetRepository;

    private final UserRepository userRepository;

    public AssetServiceImpl(
            AssetRepository assetRepository,
            UserRepository userRepository) {

        this.assetRepository =
                assetRepository;

        this.userRepository =
                userRepository;
    }

    @Override
    public AssetResponse createAsset(
            UUID ownerId,
            CreateAssetRequest request) {

        User owner =
                userRepository.findById(ownerId)
                        .orElseThrow(
                                () -> new IllegalArgumentException(
                                        "Asset owner not found"
                                )
                        );

        Asset asset =
                Asset.builder()
                        .owner(owner)
                        .type(request.type())
                        .description(
                                request.description()
                        )
                        .declaredValue(
                                request.declaredValue()
                        )
                        .ownershipStatus(
                                request.ownershipStatus()
                        )
                        .country(
                                request.country()
                        )
                        .locationReference(
                                request.locationReference()
                        )
                        .build();

        Asset saved =
                assetRepository.save(asset);

        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AssetResponse getAsset(
            UUID assetId) {

        Asset asset =
                assetRepository.findById(assetId)
                        .orElseThrow(
                                () -> new IllegalArgumentException(
                                        "Asset not found"
                                )
                        );

        return toResponse(asset);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetResponse> getOwnerAssets(
            UUID ownerId) {

        return assetRepository
                .findByOwnerId(ownerId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private AssetResponse toResponse(
            Asset asset) {

        return new AssetResponse(
                asset.getId(),
                asset.getOwner().getId(),
                asset.getType(),
                asset.getDescription(),
                asset.getDeclaredValue(),
                asset.getVerifiedValue(),
                asset.getOwnershipStatus(),
                asset.getStatus(),
                asset.getCountry(),
                asset.getLocationReference(),
                asset.getCreatedAt(),
                asset.getUpdatedAt(),
                asset.getVerifiedAt()
        );
    }
}