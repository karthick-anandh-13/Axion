package com.axion.kyc.storage;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public interface DocumentStorageService {

    String store(
            MultipartFile file,
            UUID customerId,
            UUID documentId
    );

    byte[] read(
            String storageReference
    );

    void delete(String storageReference);
}