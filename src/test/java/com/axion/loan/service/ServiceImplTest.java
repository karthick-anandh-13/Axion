package com.axion.loan.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.axion.loan.entity.Loan;
import com.axion.loan.repository.*;
import com.axion.loan.schedule.*;
import com.axion.loan.service.impl.LoanServiceImpl;
import com.axion.offer.entity.*;
import com.axion.offer.repository.LoanOfferRepository;

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

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateLoanSuccessfully() {

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
                .thenReturn(List.of());

        UUID loanId = loanService.createLoanFromOffer(offerId);

        assertNotNull(loanId);

        verify(offerRepository).findById(offerId);
        verify(loanRepository).save(any(Loan.class));
        verify(scheduleGenerator).generateSchedule(any(Loan.class));
        verify(installmentRepository).saveAll(anyList());
        verify(offerRepository).save(any(LoanOffer.class));
    }
}