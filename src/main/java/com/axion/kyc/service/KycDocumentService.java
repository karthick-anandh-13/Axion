package com.axion.kyc.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.axion.kyc.dto.CreateKycDocumentRequest;
import com.axion.kyc.dto.KycDocumentResponse;

public interface KycDocumentService {

    KycDocumentResponse createDocument(
            UUID userId,
            CreateKycDocumentRequest request,
            MultipartFile file
    );

    List<KycDocumentResponse> getMyDocuments(
            UUID userId
    );

    KycDocumentResponse getMyDocument(
            UUID userId,
            UUID documentId
    );

    void deleteMyDocument(
            UUID userId,
            UUID documentId
    );
}