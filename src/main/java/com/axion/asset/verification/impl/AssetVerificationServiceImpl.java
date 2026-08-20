package com.axion.asset.verification.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axion.asset.entity.Asset;
import com.axion.asset.entity.AssetDocument;
import com.axion.asset.entity.AssetDocumentStatus;
import com.axion.asset.entity.AssetStatus;
import com.axion.asset.repository.AssetDocumentRepository;
import com.axion.asset.repository.AssetRepository;
import com.axion.asset.verification.AssetVerificationResult;
import com.axion.asset.verification.AssetVerificationService;

@Service
@Transactional
public class AssetVerificationServiceImpl
        implements AssetVerificationService {

    private final AssetRepository assetRepository;

    private final AssetDocumentRepository documentRepository;

    public AssetVerificationServiceImpl(
            AssetRepository assetRepository,
            AssetDocumentRepository documentRepository) {

        this.assetRepository =
                assetRepository;

        this.documentRepository =
                documentRepository;
    }

    @Override
    public AssetVerificationResult verifyAsset(
            UUID assetId) {

        Asset asset =
                assetRepository.findById(assetId)
                        .orElseThrow(
                                () -> new IllegalArgumentException(
                                        "Asset not found"
                                )
                        );

        List<AssetDocument> documents =
                documentRepository
                        .findByAssetId(assetId);

        List<String> factors =
                new ArrayList<>();

        List<String> warnings =
                new ArrayList<>();

        if (documents.isEmpty()) {

            asset.setStatus(
                    AssetStatus.DOCUMENT_PENDING
            );

            assetRepository.save(asset);

            return new AssetVerificationResult(
                    AssetStatus.DOCUMENT_PENDING,
                    null,
                    0.0,
                    List.of(),
                    List.of(
                            "No asset documents submitted."
                    )
            );
        }

        boolean hasUsableDocument = false;

        for (AssetDocument document : documents) {

            if (document.getStatus()
                    == AssetDocumentStatus.UPLOADED
                    || document.getStatus()
                    == AssetDocumentStatus.VERIFIED) {

                hasUsableDocument = true;

                factors.add(
                        "Asset document available: "
                                + document.getDocumentType()
                );
            }

            if (document.getStatus()
                    == AssetDocumentStatus.REJECTED) {

                warnings.add(
                        "Rejected document: "
                                + document.getDocumentType()
                );
            }
        }

        if (!hasUsableDocument) {

            asset.setStatus(
                    AssetStatus.REJECTED
            );

            assetRepository.save(asset);

            return new AssetVerificationResult(
                    AssetStatus.REJECTED,
                    null,
                    0.0,
                    factors,
                    warnings
            );
        }

        /*
         * This is deliberately conservative.
         *
         * We currently have evidence that a document
         * exists, but we have not yet implemented
         * actual ownership/value verification.
         */
        asset.setStatus(
                AssetStatus.PARTIALLY_VERIFIED
        );

        asset.setVerifiedValue(
                BigDecimal.ZERO
        );

        assetRepository.save(asset);

        factors.add(
                "Document presence verified."
        );

        warnings.add(
                "Ownership and asset value verification "
                        + "are not yet automated."
        );

        return new AssetVerificationResult(
                AssetStatus.PARTIALLY_VERIFIED,
                BigDecimal.ZERO,
                0.50,
                factors,
                warnings
        );
    }
}