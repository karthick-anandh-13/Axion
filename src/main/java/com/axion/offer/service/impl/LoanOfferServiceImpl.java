package com.axion.offer.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axion.borrowing.entity.BorrowingRequest;
import com.axion.borrowing.repository.BorrowingRequestRepository;
import com.axion.lending.entity.LendingPartner;
import com.axion.lending.repository.LendingPartnerRepository;
import com.axion.matching.dto.MatchResponse;
import com.axion.matching.service.MatchingService;
import com.axion.offer.dto.LoanOfferResponse;
import com.axion.offer.entity.LoanOffer;
import com.axion.offer.repository.LoanOfferRepository;
import com.axion.offer.service.LoanOfferService;

@Service
@Transactional
public class LoanOfferServiceImpl implements LoanOfferService {

    private static final BigDecimal PLATFORM_FEE_RATE =
            new BigDecimal("0.02");      // 2%

    private final MatchingService matchingService;
    private final BorrowingRequestRepository requestRepository;
    private final LendingPartnerRepository partnerRepository;
    private final LoanOfferRepository offerRepository;

    public LoanOfferServiceImpl(
            MatchingService matchingService,
            BorrowingRequestRepository requestRepository,
            LendingPartnerRepository partnerRepository,
            LoanOfferRepository offerRepository) {

        this.matchingService = matchingService;
        this.requestRepository = requestRepository;
        this.partnerRepository = partnerRepository;
        this.offerRepository = offerRepository;
    }

    @Override
    public List<LoanOfferResponse> generateOffers(UUID requestId) {

        BorrowingRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Borrowing request not found"));

        List<MatchResponse> matches =
                matchingService.getTopMatches(requestId);

        List<LoanOffer> offers = matches.stream().map(match -> {

            LendingPartner partner =
                    partnerRepository.findById(match.partnerId())
                            .orElseThrow();

            BigDecimal emi = calculateEmi(
                    request.getRequestedAmount(),
                    match.offeredApr(),
                    request.getRequestedTenureMonths()
            );

            BigDecimal platformFee =
                    request.getRequestedAmount()
                            .multiply(PLATFORM_FEE_RATE)
                            .setScale(2, RoundingMode.HALF_UP);

            return LoanOffer.builder()
                    .borrowingRequest(request)
                    .lendingPartner(partner)
                    .principal(request.getRequestedAmount())
                    .apr(match.offeredApr())
                    .tenureMonths(request.getRequestedTenureMonths())
                    .monthlyEmi(emi)
                    .platformFee(platformFee)
                    .build();

        }).toList();

        offerRepository.saveAll(offers);

        return offers.stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanOfferResponse> getOffers(UUID requestId) {

        return offerRepository
                .findByBorrowingRequestId(requestId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private BigDecimal calculateEmi(
            BigDecimal principal,
            BigDecimal apr,
            int months) {

        MathContext mc = new MathContext(20, RoundingMode.HALF_UP);

        BigDecimal r = apr
                .divide(new BigDecimal("1200"), mc);

        if (r.compareTo(BigDecimal.ZERO) == 0) {
            return principal
                    .divide(new BigDecimal(months), 2, RoundingMode.HALF_UP);
        }

        BigDecimal onePlusR = BigDecimal.ONE.add(r, mc);
        BigDecimal power = onePlusR.pow(months, mc);

        BigDecimal numerator =
                principal.multiply(r, mc).multiply(power, mc);

        BigDecimal denominator =
                power.subtract(BigDecimal.ONE, mc);

        return numerator
                .divide(denominator, 2, RoundingMode.HALF_UP);
    }

    private LoanOfferResponse toResponse(LoanOffer offer) {

        return new LoanOfferResponse(
                offer.getId(),
                offer.getLendingPartner().getId(),
                offer.getLendingPartner().getOrganizationName(),
                offer.getPrincipal(),
                offer.getApr(),
                offer.getTenureMonths(),
                offer.getMonthlyEmi(),
                offer.getPlatformFee(),
                offer.getStatus(),
                offer.getExpiresAt()
        );
    }
}