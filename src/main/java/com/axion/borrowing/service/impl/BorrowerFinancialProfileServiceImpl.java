package com.axion.borrowing.service.impl;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axion.authentication.entity.User;
import com.axion.authentication.repository.UserRepository;
import com.axion.borrowing.dto.CreateFinancialProfileRequest;
import com.axion.borrowing.dto.FinancialProfileResponse;
import com.axion.borrowing.entity.BorrowerFinancialProfile;
import com.axion.borrowing.repository.BorrowerFinancialProfileRepository;
import com.axion.borrowing.service.BorrowerFinancialProfileService;

@Service
@Transactional
public class BorrowerFinancialProfileServiceImpl
        implements BorrowerFinancialProfileService {

    private final BorrowerFinancialProfileRepository profileRepository;

    private final UserRepository userRepository;

    public BorrowerFinancialProfileServiceImpl(
            BorrowerFinancialProfileRepository profileRepository,
            UserRepository userRepository) {

        this.profileRepository =
                profileRepository;

        this.userRepository =
                userRepository;
    }

    @Override
    public FinancialProfileResponse createOrUpdateProfile(
            UUID borrowerId,
            CreateFinancialProfileRequest request) {

        User borrower =
                userRepository.findById(borrowerId)
                        .orElseThrow(
                                () -> new IllegalArgumentException(
                                        "Borrower not found"
                                )
                        );

        BorrowerFinancialProfile profile =
                profileRepository
                        .findByBorrowerId(borrowerId)
                        .orElseGet(
                                () -> BorrowerFinancialProfile
                                        .builder()
                                        .borrower(borrower)
                                        .build()
                        );

        profile.setMonthlyIncome(
                request.monthlyIncome()
        );

        profile.setMonthlyExpenses(
                request.monthlyExpenses()
        );

        profile.setExistingDebt(
                request.existingDebt()
        );

        profile.setMonthlyDebtObligation(
                request.monthlyDebtObligation()
        );

        profile.setEmploymentType(
                request.employmentType()
        );

        profile.setEmployerName(
                request.employerName()
        );

        profile.setEmploymentDurationMonths(
                request.employmentDurationMonths()
        );

        /*
         * Updating financial information invalidates
         * previous verification.
         */
        profile.setIncomeVerificationStatus(
                com.axion.borrowing.entity
                        .IncomeVerificationStatus
                        .NOT_SUBMITTED
        );

        BorrowerFinancialProfile saved =
                profileRepository.save(profile);

        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public FinancialProfileResponse getProfile(
            UUID borrowerId) {

        BorrowerFinancialProfile profile =
                profileRepository
                        .findByBorrowerId(borrowerId)
                        .orElseThrow(
                                () -> new IllegalArgumentException(
                                        "Financial profile not found"
                                )
                        );

        return toResponse(profile);
    }

    private FinancialProfileResponse toResponse(
            BorrowerFinancialProfile profile) {

        BigDecimal disposableIncome =
                profile.getMonthlyIncome()
                        .subtract(
                                profile.getMonthlyExpenses()
                        )
                        .subtract(
                                profile.getMonthlyDebtObligation()
                        );

        return new FinancialProfileResponse(
                profile.getId(),
                profile.getBorrower().getId(),
                profile.getMonthlyIncome(),
                profile.getMonthlyExpenses(),
                profile.getExistingDebt(),
                profile.getMonthlyDebtObligation(),
                disposableIncome,
                profile.getEmploymentType(),
                profile.getEmployerName(),
                profile.getEmploymentDurationMonths(),
                profile.getIncomeVerificationStatus(),
                profile.getCreatedAt(),
                profile.getUpdatedAt()
        );
    }
}