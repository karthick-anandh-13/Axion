package com.axion.ai.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import com.axion.ai.dto.FaceMatchResult;
import com.axion.ai.dto.OcrAnalysisResponse;

@Component
public class KycAiClient {

    private final RestClient restClient;

    public KycAiClient(
            RestClient.Builder builder,
            @Value("${axion.ai.base-url}") String baseUrl) {

        this.restClient = builder
                .baseUrl(baseUrl)
                .build();
    }

    public OcrAnalysisResponse analyze(
            byte[] fileBytes,
            String filename,
            String contentType) {

        ByteArrayResource resource = new ByteArrayResource(fileBytes) {
            @Override
            public String getFilename() {
                return filename;
            }
        };

            MultiValueMap<String, Object> body =
                    new LinkedMultiValueMap<>();

            body.add("file", resource);

        return restClient.post()
                .uri("/api/v1/analyze")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body)
                .retrieve()
                .body(OcrAnalysisResponse.class);
    }

    public FaceMatchResult verifyFace(
            byte[] documentImage,
            String documentFilename,
            byte[] selfieImage,
            String selfieFilename) {
        throw new UnsupportedOperationException("Face verification is not configured.");
    }
}