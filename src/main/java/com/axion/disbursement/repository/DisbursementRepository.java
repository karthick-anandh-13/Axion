package com.axion.disbursement.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.axion.disbursement.entity.Disbursement;

public interface DisbursementRepository
        extends JpaRepository<Disbursement, UUID> {

    Optional<Disbursement> findByLoanId(UUID loanId);

}