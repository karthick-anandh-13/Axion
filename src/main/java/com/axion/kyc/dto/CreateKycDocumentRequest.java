package com.axion.kyc.dto;

import java.time.LocalDate;

import com.axion.kyc.entity.KycDocumentType;

import jakarta.validation.constraints.NotNull;

public class CreateKycDocumentRequest {

    @NotNull(message = "Document type is required")
    private KycDocumentType documentType;

    private LocalDate issuedDate;

    private LocalDate expiryDate;

    public CreateKycDocumentRequest() {
    }

    public KycDocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(KycDocumentType documentType) {
        this.documentType = documentType;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}