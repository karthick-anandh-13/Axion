package com.axion.ai.service.impl;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.axion.ai.dto.CreditInsightResponse;
import com.axion.ai.service.CreditInsightService;
import com.axion.authentication.entity.User;
import com.axion.authentication.service.UserService;
import com.axion.borrowing.entity.BorrowerFinancialProfile;
import com.axion.borrowing.repository.BorrowerFinancialProfileRepository;
import com.axion.customer.entity.Customer;
import com.axion.customer.repository.CustomerRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreditInsightServiceImpl implements CreditInsightService {

    @NonNull
    private final UserService userService;

    @NonNull
    private final CustomerRepository customerRepository;

    @NonNull
    private final BorrowerFinancialProfileRepository profileRepository;

    @Override
    public CreditInsightResponse generateInsight(UUID userId) {

        User user = userService.getUserById(userId);

        Customer customer = customerRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Customer profile not found."));

        BorrowerFinancialProfile profile =
                profileRepository.findByBorrowerId(customer.getId())
                        .orElse(null);

        if (profile == null) {
            return new CreditInsightResponse(
                    600,
                    "B",
                    "MEDIUM",
                    70.0,
                    30.0,
                    "Financial profile is incomplete.",
                    "Complete income verification to improve your score."
            );
        }

        BigDecimal income = profile.getMonthlyIncome();
        BigDecimal debt = profile.getMonthlyDebtObligation();

        double ratio = 0;

        if (income.compareTo(BigDecimal.ZERO) > 0) {
            ratio = debt.divide(income, 4, java.math.RoundingMode.HALF_UP)
                    .doubleValue();
        }

        int score = Math.max(300, 850 - (int) (ratio * 400));

        String rating =
                score >= 750 ? "A"
                        : score >= 680 ? "B"
                        : "C";

        String risk =
                score >= 750 ? "LOW"
                        : score >= 680 ? "MEDIUM"
                        : "HIGH";

        double repayment = Math.min(98, Math.max(55, score / 8.7));

        double defaultRisk = 100 - repayment;

        return new CreditInsightResponse(
                score,
                rating,
                risk,
                repayment,
                defaultRisk,
                "Debt-to-income ratio: " + Math.round(ratio * 100) + "%",
                ratio < 0.35
                        ? "Eligible for competitive lending offers."
                        : "Reduce monthly debt to improve borrowing capacity."
        );
    }
}