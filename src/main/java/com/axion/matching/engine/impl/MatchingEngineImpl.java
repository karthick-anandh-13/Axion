package com.axion.matching.engine.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axion.borrowing.entity.BorrowingRequest;
import com.axion.borrowing.repository.BorrowingRequestRepository;
import com.axion.lending.entity.LendingCapacity;
import com.axion.lending.entity.LendingPartner;
import com.axion.lending.entity.LendingPartnerStatus;
import com.axion.lending.repository.LendingCapacityRepository;
import com.axion.lending.repository.LendingPartnerRepository;
import com.axion.matching.dto.MatchResponse;
import com.axion.matching.engine.MatchingEngine;

@Service
@Transactional(readOnly = true)
public class MatchingEngineImpl implements MatchingEngine {

    private final BorrowingRequestRepository borrowingRequestRepository;
    private final LendingPartnerRepository partnerRepository;
    private final LendingCapacityRepository capacityRepository;

    public MatchingEngineImpl(
            BorrowingRequestRepository borrowingRequestRepository,
            LendingPartnerRepository partnerRepository,
            LendingCapacityRepository capacityRepository) {

        this.borrowingRequestRepository = borrowingRequestRepository;
        this.partnerRepository = partnerRepository;
        this.capacityRepository = capacityRepository;
    }

    @Override
    public List<MatchResponse> generateMatches(UUID borrowingRequestId) {

        BorrowingRequest request = borrowingRequestRepository
                .findById(borrowingRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Borrowing request not found"));

        List<LendingPartner> partners = partnerRepository.findAll();

        return partners.stream()

                .filter(p -> p.getStatus() == LendingPartnerStatus.ACTIVE)

                .map(partner -> {

                    LendingCapacity capacity = capacityRepository
                            .findByPartnerId(partner.getId())
                            .orElse(null);

                    if (capacity == null) return null;

                    double score = calculateScore(request, capacity);

                    BigDecimal offeredApr = capacity.getMinimumApr()
                            .add(capacity.getMaximumApr())
                            .divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP);

                    return new MatchResponse(
                            partner.getId(),
                            partner.getOrganizationName(),
                            score,
                            offeredApr,
                            capacity.getAvailableCapital()
                    );

                })

                .filter(match -> match != null)

                .sorted(Comparator.comparing(MatchResponse::score).reversed())

                .limit(5)

                .toList();
    }

    private double calculateScore(
            BorrowingRequest request,
            LendingCapacity capacity) {

        double score = 0;

        // Capital (35)
        if (capacity.getAvailableCapital()
                .compareTo(request.getRequestedAmount()) >= 0) {

            score += 35;
        }

        // APR (25)
        if (request.getMaximumAcceptableApr() != null &&
                capacity.getMinimumApr()
                        .compareTo(request.getMaximumAcceptableApr()) <= 0) {

            score += 25;
        }

        // Funding fit (20)
        BigDecimal ratio = request.getRequestedAmount()
                .divide(capacity.getAvailableCapital(), 4, RoundingMode.HALF_UP);

        if (ratio.compareTo(new BigDecimal("0.25")) <= 0)
            score += 20;
        else if (ratio.compareTo(new BigDecimal("0.50")) <= 0)
            score += 15;
        else
            score += 8;

        // Purpose placeholder (20)
        score += 20;

        return score;
    }
}