package com.axion.kyc.service;

import java.util.UUID;

import org.springframework.lang.NonNull;

import com.axion.kyc.dto.KycResponse;

public interface KycService {

    @NonNull
    KycResponse createKyc(
            @NonNull UUID userId
    );

    @NonNull
    KycResponse getMyKyc(
            @NonNull UUID userId
    );

    @NonNull
    KycResponse submitKyc(
            @NonNull UUID userId
    );
}