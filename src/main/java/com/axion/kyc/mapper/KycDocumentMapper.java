package com.axion.kyc.mapper;

import com.axion.kyc.dto.KycDocumentResponse;
import com.axion.kyc.entity.KycDocument;

public final class KycDocumentMapper {

    private KycDocumentMapper() {
        // Prevent instantiation
    }

    public static KycDocumentResponse toResponse(
            KycDocument document) {

        KycDocumentResponse response =
                new KycDocumentResponse();

        response.setId(document.getId());

        response.setDocumentType(
                document.getDocumentType()
        );

        response.setStatus(
                document.getStatus()
        );

        response.setOriginalFileName(
                document.getOriginalFileName()
        );

        response.setContentType(
                document.getContentType()
        );

        response.setFileSize(
                document.getFileSize()
        );

        response.setIssuedDate(
                document.getIssuedDate()
        );

        response.setExpiryDate(
                document.getExpiryDate()
        );

        response.setRejectionReason(
                document.getRejectionReason()
        );

        response.setVerifiedAt(
                document.getVerifiedAt()
        );

        response.setCreatedAt(
                document.getCreatedAt()
        );

        response.setUpdatedAt(
                document.getUpdatedAt()
        );

        response.setAnalysisStatus(
                document.getAnalysisStatus()
        );

        response.setAnalysisConfidence(
                document.getAnalysisConfidence()
        );

        response.setAnalysisReason(
                document.getAnalysisReason()
        );

        return response;
    }
}