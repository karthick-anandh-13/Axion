package com.axion.storage;

import java.io.IOException;
import java.io.InputStream;

public interface FileStorageService {

    String store(
            InputStream inputStream,
            String fileName,
            String contentType
    ) throws IOException;

    InputStream load(
            String storageReference
    ) throws IOException;

    void delete(
            String storageReference
    ) throws IOException;
}