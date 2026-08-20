package com.axion.kyc.storage;

import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class KycFileValidationServiceImpl
        implements KycFileValidationService {

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "application/pdf",
            "image/jpeg",
            "image/png"
    );

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
            ".pdf",
            ".jpg",
            ".jpeg",
            ".png"
    );

    private final long maxFileSize;

    public KycFileValidationServiceImpl(
            @Value("${axion.storage.max-file-size:10485760}")
            long maxFileSize) {

        this.maxFileSize = maxFileSize;
    }

    @Override
    public void validate(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "KYC document cannot be empty."
            );
        }

        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException(
                    "KYC document exceeds the maximum allowed size."
            );
        }

        String contentType = file.getContentType();

        if (contentType == null
                || !ALLOWED_CONTENT_TYPES.contains(
                        contentType.toLowerCase(Locale.ROOT))) {

            throw new IllegalArgumentException(
                    "Unsupported document type. "
                    + "Allowed formats: PDF, JPG and PNG."
            );
        }

        String filename = file.getOriginalFilename();

        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException(
                    "Document filename is required."
            );
        }

        String extension = getExtension(filename);

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException(
                    "Unsupported document extension."
            );
        }
    }

    private String getExtension(String filename) {

        int lastDot = filename.lastIndexOf('.');

        if (lastDot < 0) {
            return "";
        }

        return filename
                .substring(lastDot)
                .toLowerCase(Locale.ROOT);
    }
}