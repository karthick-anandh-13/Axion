package com.axion.portfolio.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.axion.asset.entity.Asset;
import com.axion.asset.repository.AssetRepository;
import com.axion.authentication.entity.User;
import com.axion.authentication.service.UserService;
import com.axion.customer.entity.Customer;
import com.axion.customer.repository.CustomerRepository;
import com.axion.loan.entity.Loan;
import com.axion.loan.entity.LoanStatus;
import com.axion.loan.repository.LoanRepository;
import com.axion.portfolio.dto.PortfolioSummaryResponse;
import com.axion.portfolio.service.PortfolioService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    @NonNull
    private final UserService userService;

    @NonNull
    private final CustomerRepository customerRepository;

    @NonNull
    private final LoanRepository loanRepository;

    @NonNull
    private final AssetRepository assetRepository;

    @Override
    public PortfolioSummaryResponse getPortfolioSummary(UUID userId) {

        User user = userService.getUserById(userId);

        Customer customer = customerRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Customer profile not found."));

        List<Loan> loans = loanRepository
                .findByAcceptedOfferBorrowingRequestBorrowerId(user.getId());

        List<Asset> assets = assetRepository.findByOwnerId(user.getId());

        BigDecimal totalBorrowed = loans.stream()
                .map(Loan::getPrincipal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal outstanding = loans.stream()
                .map(Loan::getOutstandingBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal assetValue = assets.stream()
                .map(Asset::getDeclaredValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long active = loans.stream()
                .filter(l -> l.getStatus() == LoanStatus.ACTIVE)
                .count();

        return new PortfolioSummaryResponse(
                customer.getId(),
                customer.getFirstName() + " " + customer.getLastName(),
                loans.size(),
                (int) active,
                totalBorrowed,
                outstanding,
                assetValue
        );
    }
}
