package com.axion.ai.service;

import com.axion.ai.dto.FaceMatchResult;

public interface FaceVerificationService {
    FaceMatchResult verify(byte[] documentImage, byte[] selfieImage);
}
