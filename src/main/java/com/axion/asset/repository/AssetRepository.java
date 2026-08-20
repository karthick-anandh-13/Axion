package com.axion.asset.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axion.asset.entity.Asset;
import com.axion.asset.entity.AssetStatus;

@Repository
public interface AssetRepository
        extends JpaRepository<Asset, UUID> {

    List<Asset> findByOwnerId(
            UUID ownerId
    );

    List<Asset> findByOwnerIdAndStatus(
            UUID ownerId,
            AssetStatus status
    );
}