package com.axion.kyc.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import com.axion.kyc.dto.CreateKycDocumentRequest;
import com.axion.kyc.dto.KycDocumentResponse;
import com.axion.kyc.service.KycDocumentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/kyc/me/documents")
public class KycDocumentController {

    private final KycDocumentService kycDocumentService;

    public KycDocumentController(
            KycDocumentService kycDocumentService) {

        this.kycDocumentService = kycDocumentService;
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<KycDocumentResponse> uploadDocument(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @ModelAttribute CreateKycDocumentRequest request,
            @RequestParam("file") MultipartFile file) {

        UUID userId = getUserId(jwt);

        KycDocumentResponse response =
                kycDocumentService.createDocument(
                        userId,
                        request,
                        file
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<KycDocumentResponse>>
            getMyDocuments(
                    @AuthenticationPrincipal Jwt jwt) {

        UUID userId = getUserId(jwt);

        return ResponseEntity.ok(
                kycDocumentService.getMyDocuments(userId)
        );
    }

    @GetMapping("/{documentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<KycDocumentResponse> getMyDocument(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID documentId) {

        UUID userId = getUserId(jwt);

        return ResponseEntity.ok(
                kycDocumentService.getMyDocument(
                        userId,
                        documentId
                )
        );
    }

    @DeleteMapping("/{documentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteMyDocument(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID documentId) {

        UUID userId = getUserId(jwt);

        kycDocumentService.deleteMyDocument(
                userId,
                documentId
        );

        return ResponseEntity.noContent().build();
    }

    private UUID getUserId(Jwt jwt) {

        return UUID.fromString(
                jwt.getSubject()
        );
    }
}