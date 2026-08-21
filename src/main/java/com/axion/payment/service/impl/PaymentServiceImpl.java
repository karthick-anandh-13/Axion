package com.axion.payment.service.impl;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axion.ledger.entity.AccountType;
import com.axion.ledger.service.LedgerService;
import com.axion.loan.entity.Loan;
import com.axion.loan.entity.LoanStatus;
import com.axion.loan.entity.RepaymentStatus;
import com.axion.loan.repository.EmiInstallmentRepository;
import com.axion.loan.repository.LoanRepository;
import com.axion.loan.schedule.EmiInstallment;
import com.axion.notification.entity.NotificationType;
import com.axion.notification.service.NotificationService;
import com.axion.payment.dto.CreatePaymentRequest;
import com.axion.payment.entity.Payment;
import com.axion.payment.entity.PaymentStatus;
import com.axion.payment.repository.PaymentRepository;
import com.axion.payment.service.PaymentService;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final EmiInstallmentRepository installmentRepository;
    private final LoanRepository loanRepository;
    private final PaymentRepository paymentRepository;
    private final LedgerService ledgerService;
    private final NotificationService notificationService;

    public PaymentServiceImpl(
            EmiInstallmentRepository installmentRepository,
            LoanRepository loanRepository,
            PaymentRepository paymentRepository,
            LedgerService ledgerService,
            NotificationService notificationService) {

        this.installmentRepository = installmentRepository;
        this.loanRepository = loanRepository;
        this.paymentRepository = paymentRepository;
        this.ledgerService = ledgerService;
        this.notificationService = notificationService;
    }

    @Override
    public @NonNull UUID payInstallment(
            @NonNull UUID installmentId,
            @NonNull CreatePaymentRequest request) {

        EmiInstallment installment = installmentRepository.findById(installmentId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Installment not found"));

        if (installment.getStatus() == RepaymentStatus.PAID) {
            throw new IllegalStateException("Installment already paid");
        }

        if (request.amount().compareTo(installment.getEmiAmount()) != 0) {
            throw new IllegalArgumentException("EMI amount mismatch");
        }

        Payment payment = Payment.builder()
                .installment(installment)
                .amount(request.amount())
                .paymentMethod(request.paymentMethod())
                .status(PaymentStatus.SUCCESS)
                .paymentReference(generateReference())
                .build();

        payment = Objects.requireNonNull(
                paymentRepository.save(payment),
                "Saved payment cannot be null");

        installment.setStatus(RepaymentStatus.PAID);
        installmentRepository.save(installment);

        Loan loan = installment.getLoan();

        BigDecimal newBalance = loan.getOutstandingBalance()
                .subtract(installment.getPrincipalComponent());

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            newBalance = BigDecimal.ZERO;
        }

        loan.setOutstandingBalance(newBalance);

        if (newBalance.compareTo(BigDecimal.ZERO) == 0) {
            loan.setStatus(LoanStatus.COMPLETED);
        } else {
            loan.setStatus(LoanStatus.ACTIVE);
        }

        loanRepository.save(loan);

        ledgerService.createTransaction(
                "EMI Payment - " + payment.getPaymentReference(),
                AccountType.CASH,
                AccountType.LOAN_RECEIVABLE,
                payment.getAmount());

        notificationService.sendNotification(
                loan.getAcceptedOffer()
                        .getBorrowingRequest()
                        .getBorrower()
                        .getId(),
                NotificationType.EMI_RECEIPT,
                "EMI Payment Successful",
                "Your EMI payment of ₹" + payment.getAmount()
                        + " has been received successfully.");

        return Objects.requireNonNull(payment.getId());
    }

    private String generateReference() {
        return "PAY-"
                + System.currentTimeMillis()
                + "-"
                + UUID.randomUUID().toString().substring(0, 6);
    }
}