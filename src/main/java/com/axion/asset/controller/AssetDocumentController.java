package com.axion.asset.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.axion.asset.dto.AssetDocumentResponse;
import com.axion.asset.entity.AssetDocumentType;
import com.axion.asset.service.AssetDocumentService;

@RestController
@RequestMapping("/api/v1/assets")
public class AssetDocumentController {

    private final AssetDocumentService documentService;

    public AssetDocumentController(
            AssetDocumentService documentService) {

        this.documentService =
                documentService;
    }

    @PostMapping(
            value = "/{assetId}/documents",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<AssetDocumentResponse>
    uploadDocument(
            @PathVariable UUID assetId,

            @RequestParam
            AssetDocumentType documentType,

            @RequestParam("file")
            MultipartFile file) {

        AssetDocumentResponse response =
                documentService.uploadDocument(
                        assetId,
                        documentType,
                        file
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{assetId}/documents")
    public ResponseEntity<List<AssetDocumentResponse>>
    getDocuments(
            @PathVariable UUID assetId) {

        return ResponseEntity.ok(
                documentService.getAssetDocuments(
                        assetId
                )
        );
    }
}