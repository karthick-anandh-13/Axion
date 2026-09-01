package com.axion.repayment.service.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.axion.repayment.dto.RepaymentSummaryResponse;
import com.axion.repayment.service.RepaymentService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RepaymentServiceImpl implements RepaymentService {

    @PersistenceContext
    @NonNull
    private final EntityManager entityManager;

    @Override
    @NonNull
    public RepaymentSummaryResponse getRepaymentSummary(@NonNull UUID userId) {

        BigDecimal totalOutstanding = BigDecimal.ZERO;
        BigDecimal totalPaid = BigDecimal.ZERO;
        BigDecimal nextEmiAmount = BigDecimal.ZERO;
        LocalDate nextDueDate = LocalDate.now();
        int activeLoans = 0;
        int pendingInstallments = 0;
        int overdueInstallments = 0;

        // Total outstanding & active loans
        List<?> loanResult = entityManager.createNativeQuery("""
                SELECT
                    COALESCE(SUM(l.outstanding_balance), 0),
                    COUNT(l.id)
                FROM loans l
                JOIN loan_offers lo ON l.offer_id = lo.id
                JOIN borrowing_requests br ON lo.request_id = br.id
                JOIN customers c ON br.borrower_id = c.id
                WHERE c.user_id = CAST(:userId AS UUID)
                  AND l.status = 'ACTIVE'
                """)
                .setParameter("userId", userId)
                .getResultList();

        if (!loanResult.isEmpty()) {
            Object[] row = (Object[]) loanResult.getFirst();
            totalOutstanding = (BigDecimal) row[0];
            activeLoans = ((Number) row[1]).intValue();
        }

        // Total paid
        List<?> paidResult = entityManager.createNativeQuery("""
                SELECT COALESCE(SUM(p.amount), 0)
                FROM payments p
                JOIN emi_installments e ON p.installment_id = e.id
                JOIN loans l ON e.loan_id = l.id
                JOIN loan_offers lo ON l.offer_id = lo.id
                JOIN borrowing_requests br ON lo.request_id = br.id
                JOIN customers c ON br.borrower_id = c.id
                WHERE c.user_id = CAST(:userId AS UUID)
                  AND p.status = 'SUCCESS'
                """)
                .setParameter("userId", userId)
                .getResultList();

        if (!paidResult.isEmpty()) {
            totalPaid = (BigDecimal) paidResult.getFirst();
        }

        // Next EMI
        List<?> emiResult = entityManager.createNativeQuery("""
                SELECT e.emi_amount, e.due_date
                FROM emi_installments e
                JOIN loans l ON e.loan_id = l.id
                JOIN loan_offers lo ON l.offer_id = lo.id
                JOIN borrowing_requests br ON lo.request_id = br.id
                JOIN customers c ON br.borrower_id = c.id
                WHERE c.user_id = CAST(:userId AS UUID)
                  AND e.status = 'PENDING'
                ORDER BY e.due_date
                LIMIT 1
                """)
                .setParameter("userId", userId)
                .getResultList();

        if (!emiResult.isEmpty()) {
            Object[] row = (Object[]) emiResult.getFirst();
            nextEmiAmount = (BigDecimal) row[0];
            nextDueDate = ((Date) row[1]).toLocalDate();
        }

        // Pending installments
        List<?> pendingResult = entityManager.createNativeQuery("""
                SELECT COUNT(e.id)
                FROM emi_installments e
                JOIN loans l ON e.loan_id = l.id
                JOIN loan_offers lo ON l.offer_id = lo.id
                JOIN borrowing_requests br ON lo.request_id = br.id
                JOIN customers c ON br.borrower_id = c.id
                WHERE c.user_id = CAST(:userId AS UUID)
                  AND e.status = 'PENDING'
                """)
                .setParameter("userId", userId)
                .getResultList();

        if (!pendingResult.isEmpty()) {
            pendingInstallments = ((Number) pendingResult.getFirst()).intValue();
        }

        // Overdue installments
        List<?> overdueResult = entityManager.createNativeQuery("""
                SELECT COUNT(e.id)
                FROM emi_installments e
                JOIN loans l ON e.loan_id = l.id
                JOIN loan_offers lo ON l.offer_id = lo.id
                JOIN borrowing_requests br ON lo.request_id = br.id
                JOIN customers c ON br.borrower_id = c.id
                WHERE c.user_id = CAST(:userId AS UUID)
                  AND e.status = 'OVERDUE'
                """)
                .setParameter("userId", userId)
                .getResultList();

        if (!overdueResult.isEmpty()) {
            overdueInstallments = ((Number) overdueResult.getFirst()).intValue();
        }

        return new RepaymentSummaryResponse(
                totalOutstanding,
                totalPaid,
                nextEmiAmount,
                nextDueDate,
                activeLoans,
                pendingInstallments,
                overdueInstallments
        );
    }
}