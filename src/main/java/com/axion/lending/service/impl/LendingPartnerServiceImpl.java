package com.axion.lending.service.impl;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axion.authentication.entity.User;
import com.axion.authentication.repository.UserRepository;
import com.axion.lending.dto.CreateLendingPartnerRequest;
import com.axion.lending.entity.LendingCapacity;
import com.axion.lending.entity.LendingPartner;
import com.axion.lending.repository.LendingCapacityRepository;
import com.axion.lending.repository.LendingPartnerRepository;
import com.axion.lending.service.LendingPartnerService;

@Service
@Transactional
public class LendingPartnerServiceImpl implements LendingPartnerService {

    private final UserRepository userRepository;
    private final LendingPartnerRepository partnerRepository;
    private final LendingCapacityRepository capacityRepository;

    public LendingPartnerServiceImpl(
            UserRepository userRepository,
            LendingPartnerRepository partnerRepository,
            LendingCapacityRepository capacityRepository) {

        this.userRepository = userRepository;
        this.partnerRepository = partnerRepository;
        this.capacityRepository = capacityRepository;
    }

    @Override
    public void registerPartner(
            UUID userId,
            CreateLendingPartnerRequest request) {

        if (partnerRepository.findByUserId(userId).isPresent()) {
            throw new IllegalArgumentException(
                    "User is already registered as a lending partner");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        LendingPartner partner = LendingPartner.builder()
                .user(user)
                .organizationName(request.organizationName())
                .riskPreference(request.riskPreference())
                .build();

        partner = partnerRepository.save(partner);

        LendingCapacity capacity = LendingCapacity.builder()
                .partner(partner)
                .totalCapital(request.totalCapital())
                .committedCapital(BigDecimal.ZERO)
                .availableCapital(request.totalCapital())
                .minimumApr(request.minimumApr())
                .maximumApr(request.maximumApr())
                .build();

        capacityRepository.save(capacity);
    }
}