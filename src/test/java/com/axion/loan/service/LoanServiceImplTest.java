package com.axion.loan.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.axion.loan.entity.Loan;
import com.axion.loan.repository.EmiInstallmentRepository;
import com.axion.loan.repository.LoanRepository;
import com.axion.loan.schedule.EmiInstallment;
import com.axion.loan.schedule.EmiScheduleGenerator;
import com.axion.loan.service.impl.LoanServiceImpl;
import com.axion.offer.entity.LoanOffer;
import com.axion.offer.entity.LoanOfferStatus;
import com.axion.offer.repository.LoanOfferRepository;

@ExtendWith(MockitoExtension.class)
class LoanServiceImplTest {

    @Mock
    private LoanOfferRepository offerRepository;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private EmiInstallmentRepository installmentRepository;

    @Mock
    private EmiScheduleGenerator scheduleGenerator;

    @InjectMocks
    private LoanServiceImpl loanService;

    @Test
    @SuppressWarnings({"unchecked"})
    void shouldCreateLoanSuccessfully() {

        // Arrange
        UUID offerId = UUID.randomUUID();

        LoanOffer offer = LoanOffer.builder()
                .id(offerId)
                .principal(new BigDecimal("500000"))
                .apr(new BigDecimal("12.00"))
                .tenureMonths(24)
                .monthlyEmi(new BigDecimal("23561"))
                .status(LoanOfferStatus.GENERATED)
                .build();

        when(offerRepository.findById(offerId))
                .thenReturn(Optional.of(offer));

        Loan savedLoan = Loan.builder()
                .id(UUID.randomUUID())
                .principal(offer.getPrincipal())
                .build();

        when(loanRepository.save(any(Loan.class)))
                .thenReturn(savedLoan);

        when(scheduleGenerator.generateSchedule(any(Loan.class)))
                .thenReturn(Collections.<EmiInstallment>emptyList());

        // Act
        UUID loanId = loanService.createLoanFromOffer(offerId);

        // Assert
        assertNotNull(loanId);

        verify(offerRepository).findById(offerId);
        verify(loanRepository).save(any(Loan.class));
        verify(scheduleGenerator).generateSchedule(any(Loan.class));
        verify(installmentRepository).saveAll(any(Iterable.class));
        verify(offerRepository).save(any(LoanOffer.class));
    }
}