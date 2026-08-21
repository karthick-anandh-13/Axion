package com.axion.lending.repository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.axion.lending.entity.LendingPartner;
public interface LendingPartnerRepository extends JpaRepository<LendingPartner, UUID> { Optional<LendingPartner> findByUserId(UUID userId); }
