package com.axion.borrowing.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axion.borrowing.entity.BorrowerFinancialProfile;

@Repository
public interface BorrowerFinancialProfileRepository
        extends JpaRepository<BorrowerFinancialProfile, UUID> {

    Optional<BorrowerFinancialProfile>
    findByBorrowerId(UUID borrowerId);

    boolean existsByBorrowerId(UUID borrowerId);
}