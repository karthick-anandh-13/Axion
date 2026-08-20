package com.axion.kyc.service;

import java.util.UUID;

import com.axion.kyc.dto.KycResponse;

public interface KycService {

    KycResponse createKyc(UUID userId);

    KycResponse getMyKyc(UUID userId);

    KycResponse submitKyc(UUID userId);
}