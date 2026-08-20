package com.axion.loan.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.axion.loan.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, UUID> {

    Optional<Loan> findByAcceptedOfferId(UUID offerId);

}