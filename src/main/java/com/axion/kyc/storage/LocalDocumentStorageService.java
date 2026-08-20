package com.axion.kyc.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LocalDocumentStorageService
        implements DocumentStorageService {

    private final Path rootLocation;

    public LocalDocumentStorageService(
            @Value("${axion.storage.local-path:uploads/kyc}")
            String storagePath) {

        this.rootLocation = Paths.get(storagePath)
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(rootLocation);
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Could not initialize document storage.",
                    exception
            );
        }
    }

    @Override
    public String store(
            MultipartFile file,
            UUID customerId,
            UUID documentId) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "Document file cannot be empty."
            );
        }

        String originalFileName =
                file.getOriginalFilename();

        String extension = extractExtension(
                originalFileName
        );

        String storedFileName =
                documentId + extension;

        Path customerDirectory =
                rootLocation.resolve(
                        customerId.toString()
                ).normalize();

        try {

            Files.createDirectories(customerDirectory);

            Path targetFile =
                    customerDirectory
                            .resolve(storedFileName)
                            .normalize();

            if (!targetFile.startsWith(customerDirectory)) {
                throw new IllegalStateException(
                        "Invalid document storage path."
                );
            }

            Files.copy(
                    file.getInputStream(),
                    targetFile,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return targetFile.toString();

        } catch (IOException exception) {

            throw new IllegalStateException(
                    "Failed to store KYC document.",
                    exception
            );
        }
    }

    @Override
    public void delete(String storageReference) {

        if (storageReference == null
                || storageReference.isBlank()) {
            return;
        }

        try {

            Path filePath = Paths.get(
                    storageReference
            ).toAbsolutePath().normalize();

            if (!filePath.startsWith(rootLocation)) {
                throw new IllegalStateException(
                        "Invalid document deletion path."
                );
            }

            Files.deleteIfExists(filePath);

        } catch (IOException exception) {

            throw new IllegalStateException(
                    "Failed to delete KYC document.",
                    exception
            );
        }
    }

    private String extractExtension(
            String originalFileName) {

        if (originalFileName == null
                || originalFileName.isBlank()) {
            return "";
        }

        int lastDot =
                originalFileName.lastIndexOf('.');

        if (lastDot < 0) {
            return "";
        }

        return originalFileName.substring(lastDot)
                .toLowerCase();
    }
}