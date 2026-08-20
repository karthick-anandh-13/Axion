package com.axion.kyc.storage;

import org.springframework.web.multipart.MultipartFile;

public interface KycFileValidationService {

    void validate(MultipartFile file);
}