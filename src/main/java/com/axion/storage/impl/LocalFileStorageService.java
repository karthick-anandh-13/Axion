package com.axion.storage.impl;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.axion.storage.FileStorageService;
@Service
public class LocalFileStorageService implements FileStorageService {
 private final Path directory;
 public LocalFileStorageService(@Value("${axion.storage.local-path:./storage}") String path) { directory = Paths.get(path).toAbsolutePath().normalize(); try { Files.createDirectories(directory); } catch (IOException e) { throw new IllegalStateException(e); } }
 public String store(InputStream inputStream, String fileName, String contentType) throws IOException { String ref = UUID.randomUUID().toString(); Files.copy(inputStream, directory.resolve(ref)); return ref; }
 public InputStream load(String storageReference) throws IOException { return Files.newInputStream(directory.resolve(storageReference).normalize()); }
 public void delete(String storageReference) throws IOException { Files.deleteIfExists(directory.resolve(storageReference).normalize()); }
}
