package com.axion.asset.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.axion.asset.dto.AssetDocumentResponse;
import com.axion.asset.entity.Asset;
import com.axion.asset.entity.AssetDocument;
import com.axion.asset.entity.AssetDocumentStatus;
import com.axion.asset.entity.AssetDocumentType;
import com.axion.asset.entity.AssetStatus;
import com.axion.asset.repository.AssetDocumentRepository;
import com.axion.asset.repository.AssetRepository;
import com.axion.asset.service.AssetDocumentService;
import com.axion.storage.FileStorageService;

@Service
@Transactional
public class AssetDocumentServiceImpl implements AssetDocumentService {

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    private final AssetRepository assetRepository;
    private final AssetDocumentRepository documentRepository;
    private final FileStorageService fileStorageService;

    public AssetDocumentServiceImpl(
            AssetRepository assetRepository,
            AssetDocumentRepository documentRepository,
            FileStorageService fileStorageService) {

        this.assetRepository = assetRepository;
        this.documentRepository = documentRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public @NonNull AssetDocumentResponse uploadDocument(
            @NonNull UUID assetId,
            @NonNull AssetDocumentType documentType,
            @NonNull MultipartFile file) {

        validateFile(file);

        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Asset not found"));

        try {

            String fileHash = calculateSha256(file);

            String storageReference = fileStorageService.store(
                    file.getInputStream(),
                    file.getOriginalFilename(),
                    file.getContentType());

            AssetDocument document = AssetDocument.builder()
                    .asset(asset)
                    .documentType(documentType)
                    .storageReference(storageReference)
                    .fileHash(fileHash)
                    .status(AssetDocumentStatus.UPLOADED)
                    .mimeType(file.getContentType())
                    .fileSize(file.getSize())
                    .build();

            AssetDocument saved = Objects.requireNonNull(
                    documentRepository.save(document),
                    "Saved asset document must not be null");

            asset.setStatus(AssetStatus.VERIFICATION_PENDING);
            assetRepository.save(asset);

            return toResponse(saved);

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Unable to store asset document", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public @NonNull List<AssetDocumentResponse> getAssetDocuments(
            @NonNull UUID assetId) {

        if (!assetRepository.existsById(assetId)) {
            throw new IllegalArgumentException("Asset not found");
        }

        return documentRepository.findByAssetId(assetId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private void validateFile(@NonNull MultipartFile file) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Document file is required");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException(
                    "Document exceeds maximum size of 10 MB");
        }

        String contentType = file.getContentType();

        if (contentType == null || !isAllowedContentType(contentType)) {
            throw new IllegalArgumentException("Unsupported document type");
        }
    }

    private boolean isAllowedContentType(String contentType) {

        switch (contentType) {
            case "application/pdf":
            case "image/jpeg":
            case "image/png":
            case "image/webp":
                return true;
            default:
                return false;
        }
    }

    private String calculateSha256(
            MultipartFile file) throws IOException {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            try (InputStream in = file.getInputStream()) {

                byte[] buffer = new byte[8192];
                int bytesRead;

                while ((bytesRead = in.read(buffer)) != -1) {
                    digest.update(buffer, 0, bytesRead);
                }
            }

            return HexFormat.of().formatHex(digest.digest());

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "SHA-256 algorithm unavailable", e);
        }
    }

    private @NonNull AssetDocumentResponse toResponse(
            AssetDocument document) {

        return new AssetDocumentResponse(
                document.getId(),
                document.getAsset().getId(),
                document.getDocumentType(),
                document.getStatus(),
                document.getMimeType(),
                document.getFileSize(),
                document.getUploadedAt(),
                document.getVerifiedAt());
    }
}