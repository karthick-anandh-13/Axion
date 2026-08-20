package com.axion.kyc.service.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.axion.kyc.entity.DocumentAnalysisStatus;
import com.axion.kyc.entity.KycDocument;
import com.axion.kyc.service.DocumentAnalysisResult;
import com.axion.kyc.service.DocumentAnalysisService;

@Service
public class DocumentAnalysisServiceImpl
        implements DocumentAnalysisService {

    @Override
    public DocumentAnalysisResult analyze(
            KycDocument document) {

        if (document == null) {

            return new DocumentAnalysisResult(
                    DocumentAnalysisStatus.FAILED,
                    false,
                    false,
                    false,
                    0.0,
                    null,
                    null,
                    "Document does not exist."
            );
        }

        if (document.getStorageReference() == null
                || document.getStorageReference().isBlank()) {

            return new DocumentAnalysisResult(
                    DocumentAnalysisStatus.FAILED,
                    false,
                    false,
                    false,
                    0.0,
                    null,
                    null,
                    "Document storage reference is missing."
            );
        }

        boolean expired =
                document.getExpiryDate() != null
                && document.getExpiryDate()
                        .isBefore(LocalDate.now());

        if (expired) {

            return new DocumentAnalysisResult(
                    DocumentAnalysisStatus.COMPLETED,
                    true,
                    true,
                    true,
                    1.0,
                    null,
                    null,
                    "Document has expired."
            );
        }

        return new DocumentAnalysisResult(
                DocumentAnalysisStatus.COMPLETED,
                true,
                true,
                false,
                0.50,
                null,
                null,
                "Basic document validation completed. "
                + "OCR and identity analysis are not yet enabled."
        );
    }
}