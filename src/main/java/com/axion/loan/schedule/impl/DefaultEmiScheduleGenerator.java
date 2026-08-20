package com.axion.loan.schedule.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.axion.loan.entity.Loan;
import com.axion.loan.entity.RepaymentStatus;
import com.axion.loan.schedule.EmiInstallment;
import com.axion.loan.schedule.EmiScheduleGenerator;

@Component
public class DefaultEmiScheduleGenerator
        implements EmiScheduleGenerator {

    @Override
    public List<EmiInstallment> generateSchedule(Loan loan) {

        List<EmiInstallment> schedule = new ArrayList<>();

        BigDecimal balance = loan.getPrincipal();

        BigDecimal monthlyRate = loan.getApr()
                .divide(new BigDecimal("1200"), 10, RoundingMode.HALF_UP);

        LocalDate dueDate = loan.getDisbursementDate().plusMonths(1);

        for (int i = 1; i <= loan.getTenureMonths(); i++) {

            BigDecimal interest = balance
                    .multiply(monthlyRate)
                    .setScale(2, RoundingMode.HALF_UP);

            BigDecimal principal = loan.getMonthlyEmi()
                    .subtract(interest)
                    .setScale(2, RoundingMode.HALF_UP);

            if (i == loan.getTenureMonths()) {
                principal = balance;
            }

            balance = balance.subtract(principal);

            if (balance.compareTo(BigDecimal.ZERO) < 0) {
                balance = BigDecimal.ZERO;
            }

            schedule.add(
                    EmiInstallment.builder()
                            .loan(loan)
                            .installmentNumber(i)
                            .dueDate(dueDate)
                            .emiAmount(loan.getMonthlyEmi())
                            .principalComponent(principal)
                            .interestComponent(interest)
                            .remainingBalance(balance)
                            .status(RepaymentStatus.PENDING)
                            .build());

            dueDate = dueDate.plusMonths(1);
        }

        return schedule;
    }
}