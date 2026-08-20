package com.axion.customer.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axion.customer.entity.AddressType;
import com.axion.customer.entity.CustomerAddress;

@Repository
public interface CustomerAddressRepository
        extends JpaRepository<CustomerAddress, UUID> {

    List<CustomerAddress> findByCustomerId(UUID customerId);

    List<CustomerAddress> findByCustomerIdAndType(
            UUID customerId,
            AddressType type
    );

    boolean existsByCustomerIdAndType(
            UUID customerId,
            AddressType type
    );
}