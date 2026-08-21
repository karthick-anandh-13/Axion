package com.axion.loan.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axion.loan.entity.Loan;
import com.axion.loan.entity.LoanStatus;
import com.axion.loan.repository.EmiInstallmentRepository;
import com.axion.loan.repository.LoanRepository;
import com.axion.loan.schedule.EmiInstallment;
import com.axion.loan.schedule.EmiScheduleGenerator;
import com.axion.loan.service.LoanService;
import com.axion.offer.entity.LoanOffer;
import com.axion.offer.entity.LoanOfferStatus;
import com.axion.offer.repository.LoanOfferRepository;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    private final LoanOfferRepository offerRepository;
    private final LoanRepository loanRepository;
    private final EmiInstallmentRepository installmentRepository;
    private final EmiScheduleGenerator scheduleGenerator;

    public LoanServiceImpl(
            LoanOfferRepository offerRepository,
            LoanRepository loanRepository,
            EmiInstallmentRepository installmentRepository,
            EmiScheduleGenerator scheduleGenerator) {

        this.offerRepository = offerRepository;
        this.loanRepository = loanRepository;
        this.installmentRepository = installmentRepository;
        this.scheduleGenerator = scheduleGenerator;
    }

    @Override
    public @NonNull UUID createLoanFromOffer(@NonNull UUID loanOfferId) {

        LoanOffer offer = offerRepository.findById(loanOfferId)
                .orElseThrow(() -> new IllegalArgumentException("Loan offer not found"));

        if (offer.getStatus() != LoanOfferStatus.GENERATED &&
            offer.getStatus() != LoanOfferStatus.VIEWED) {
            throw new IllegalStateException("Loan offer cannot be accepted");
        }

        Loan loan = Loan.builder()
                .acceptedOffer(offer)
                .principal(offer.getPrincipal())
                .apr(offer.getApr())
                .tenureMonths(offer.getTenureMonths())
                .monthlyEmi(offer.getMonthlyEmi())
                .outstandingBalance(offer.getPrincipal())
                .disbursementDate(LocalDate.now())
                .maturityDate(LocalDate.now().plusMonths(offer.getTenureMonths()))
                .status(LoanStatus.PENDING_DISBURSEMENT)
                .build();

        loan = loanRepository.save(loan);

        List<EmiInstallment> installments = scheduleGenerator.generateSchedule(loan);
        installmentRepository.saveAll(installments);

        offer.setStatus(LoanOfferStatus.ACCEPTED);
        offerRepository.save(offer);

        return loan.getId();
    }
}