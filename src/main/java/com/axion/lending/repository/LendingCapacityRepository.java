package com.axion.lending.repository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.axion.lending.entity.LendingCapacity;
public interface LendingCapacityRepository extends JpaRepository<LendingCapacity, UUID> { Optional<LendingCapacity> findByPartnerId(UUID partnerId); }
