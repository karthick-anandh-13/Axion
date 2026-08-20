package com.axion.kyc.service;

import com.axion.kyc.entity.KycDocument;

public interface DocumentAnalysisService {

    DocumentAnalysisResult analyze(
            KycDocument document
    );
}