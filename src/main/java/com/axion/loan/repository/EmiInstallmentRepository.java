package com.axion.loan.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.axion.loan.entity.Loan;
import com.axion.loan.schedule.EmiInstallment;

public interface EmiInstallmentRepository
        extends JpaRepository<EmiInstallment, UUID> {

    List<EmiInstallment> findByLoanOrderByInstallmentNumber(Loan loan);

}