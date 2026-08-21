package com.axion.kyc.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axion.customer.entity.Customer;
import com.axion.customer.exception.CustomerNotFoundException;
import com.axion.customer.repository.CustomerRepository;
import com.axion.kyc.dto.KycResponse;
import com.axion.kyc.entity.KycStatus;
import com.axion.kyc.entity.KycVerification;
import com.axion.kyc.exception.KycAlreadyExistsException;
import com.axion.kyc.exception.KycNotFoundException;
import com.axion.kyc.mapper.KycMapper;
import com.axion.kyc.repository.KycRepository;
import com.axion.kyc.service.KycAutomationEngine;
import com.axion.kyc.service.KycService;

@Service
@Transactional
public class KycServiceImpl implements KycService {

    private final KycRepository kycRepository;
    private final CustomerRepository customerRepository;
    public KycServiceImpl(
            KycRepository kycRepository,
            CustomerRepository customerRepository,
            KycAutomationEngine kycAutomationEngine) {

        this.kycRepository = kycRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public @NonNull KycResponse createKyc(
            @NonNull UUID userId) {

        Customer customer = findCustomerByUserId(userId);

        if (kycRepository.existsByCustomerId(customer.getId())) {
            throw new KycAlreadyExistsException(
                    "KYC verification already exists.");
        }

        KycVerification kyc = KycVerification.builder()
                .customer(customer)
                .status(KycStatus.NOT_STARTED)
                .build();

        KycVerification savedKyc = kycRepository.save(kyc);

        return KycMapper.toResponse(savedKyc);
    }

    @Override
    @Transactional(readOnly = true)
    public @NonNull KycResponse getMyKyc(
            @NonNull UUID userId) {

        Customer customer = findCustomerByUserId(userId);

        KycVerification kyc = kycRepository.findByCustomerId(customer.getId())
                .orElseThrow(() ->
                        new KycNotFoundException(
                                "KYC verification not found."));

        return KycMapper.toResponse(kyc);
    }

    @Override
    public @NonNull KycResponse submitKyc(
            @NonNull UUID userId) {

        Customer customer = findCustomerByUserId(userId);

        KycVerification kyc = kycRepository.findByCustomerId(customer.getId())
                .orElseThrow(() ->
                        new KycNotFoundException(
                                "KYC verification not found."));

        if (kyc.getStatus() != KycStatus.NOT_STARTED &&
            kyc.getStatus() != KycStatus.REJECTED) {

            throw new IllegalStateException(
                    "KYC cannot be submitted in its current state.");
        }

        kyc.setStatus(KycStatus.PENDING);
        kyc.setSubmittedAt(LocalDateTime.now());
        kyc.setRejectionReason(null);

        KycVerification savedKyc = kycRepository.save(kyc);

        return KycMapper.toResponse(savedKyc);
    }

    private @NonNull Customer findCustomerByUserId(
            @NonNull UUID userId) {

        return customerRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer profile not found."));
    }
}