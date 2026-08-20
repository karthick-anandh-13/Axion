package com.axion.disbursement.service.impl;

import java.util.UUID;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axion.disbursement.dto.DisbursementResponse;
import com.axion.disbursement.entity.Disbursement;
import com.axion.disbursement.entity.DisbursementStatus;
import com.axion.disbursement.repository.DisbursementRepository;
import com.axion.disbursement.service.DisbursementService;
import com.axion.ledger.entity.AccountType;
import com.axion.ledger.service.LedgerService;
import com.axion.lending.entity.LendingCapacity;
import com.axion.lending.repository.LendingCapacityRepository;
import com.axion.loan.entity.Loan;
import com.axion.loan.entity.LoanStatus;
import com.axion.loan.repository.LoanRepository;

@Service
@Transactional
public class DisbursementServiceImpl implements DisbursementService {

    private final LoanRepository loanRepository;
    private final LendingCapacityRepository capacityRepository;
    private final DisbursementRepository disbursementRepository;
    private final LedgerService ledgerService;

    public DisbursementServiceImpl(
            LoanRepository loanRepository,
            LendingCapacityRepository capacityRepository,
            DisbursementRepository disbursementRepository,
            LedgerService ledgerService) {

        this.loanRepository = loanRepository;
        this.capacityRepository = capacityRepository;
        this.disbursementRepository = disbursementRepository;
        this.ledgerService = ledgerService;
    }

    @Override
    public DisbursementResponse disburseLoan(UUID loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Loan not found"));

        if (loan.getStatus() != LoanStatus.PENDING_DISBURSEMENT) {
            throw new IllegalStateException("Loan already disbursed");
        }

        LendingCapacity capacity =
                capacityRepository.findByPartnerId(
                        loan.getAcceptedOffer()
                                .getLendingPartner()
                                .getId())
                        .orElseThrow(() ->
                                new IllegalArgumentException("Lender capacity not found"));

        if (capacity.getAvailableCapital()
                .compareTo(loan.getPrincipal()) < 0) {

            throw new IllegalStateException("Insufficient lender capital");
        }

        capacity.setAvailableCapital(
                capacity.getAvailableCapital()
                        .subtract(loan.getPrincipal()));

        capacity.setCommittedCapital(
                capacity.getCommittedCapital()
                        .add(loan.getPrincipal()));

        capacityRepository.save(capacity);

        Disbursement disbursement = Disbursement.builder()
                .loan(loan)
                .amount(loan.getPrincipal())
                .transactionReference(generateReference())
                .status(DisbursementStatus.SUCCESS)
                .build();

        disbursementRepository.save(disbursement);

        ledgerService.createTransaction(
                "Loan Disbursement " + disbursement.getTransactionReference(),
                AccountType.LOAN_RECEIVABLE,
                AccountType.CASH,
                loan.getPrincipal());

        loan.setStatus(LoanStatus.ACTIVE);
        loanRepository.save(loan);

        return new DisbursementResponse(
                disbursement.getId(),
                loan.getId(),
                disbursement.getAmount(),
                disbursement.getTransactionReference(),
                disbursement.getStatus(),
                disbursement.getProcessedAt());
    }

    private String generateReference() {
        return "DIS-" + System.currentTimeMillis();
    }
}