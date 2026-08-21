package com.axion.kyc.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.axion.kyc.entity.DocumentAnalysisStatus;
import com.axion.kyc.entity.KycDocumentStatus;
import com.axion.kyc.entity.KycDocumentType;

public class KycDocumentResponse {

    private UUID id;

    private KycDocumentType documentType;

    private KycDocumentStatus status;

    private String originalFileName;

    private String contentType;

    private Long fileSize;

    private LocalDate issuedDate;

    private LocalDate expiryDate;

    private String rejectionReason;

    private LocalDateTime verifiedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
 
    private DocumentAnalysisStatus analysisStatus;

    private Double analysisConfidence;

    private String analysisReason;
    
    public KycDocumentResponse() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public KycDocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(KycDocumentType documentType) {
        this.documentType = documentType;
    }

    public KycDocumentStatus getStatus() {
        return status;
    }

    public void setStatus(KycDocumentStatus status) {
        this.status = status;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
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

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(LocalDateTime verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public DocumentAnalysisStatus getAnalysisStatus() {
    return analysisStatus;
    }

    public void setAnalysisStatus(
            DocumentAnalysisStatus analysisStatus) {
        this.analysisStatus = analysisStatus;
    }

    public Double getAnalysisConfidence() {
        return analysisConfidence;
    }

    public void setAnalysisConfidence(
            Double analysisConfidence) {
        this.analysisConfidence = analysisConfidence;
    }

    public String getAnalysisReason() {
        return analysisReason;
    }

    public void setAnalysisReason(String analysisReason) {
        this.analysisReason = analysisReason;
    }
}