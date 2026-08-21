package com.axion.asset.service;

import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import com.axion.asset.dto.AssetDocumentResponse;
import com.axion.asset.entity.AssetDocumentType;

public interface AssetDocumentService {

    @NonNull
    AssetDocumentResponse uploadDocument(
            @NonNull UUID assetId,
            @NonNull AssetDocumentType documentType,
            @NonNull MultipartFile file
    );

    @NonNull
    List<AssetDocumentResponse> getAssetDocuments(
            @NonNull UUID assetId
    );
}