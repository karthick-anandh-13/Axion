package com.axion.lending.service;

import java.util.UUID;

import com.axion.lending.dto.CreateLendingPartnerRequest;

public interface LendingPartnerService {

    void registerPartner(
            UUID userId,
            CreateLendingPartnerRequest request
    );
}