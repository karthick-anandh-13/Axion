package com.axion.customer.service;

import java.util.UUID;

import com.axion.customer.dto.CreateCustomerRequest;
import com.axion.customer.dto.CustomerResponse;

public interface CustomerService {

    CustomerResponse createCustomer(
            UUID userId,
            CreateCustomerRequest request
    );

    CustomerResponse getCustomerByUserId(
            UUID userId
    );

    CustomerResponse updateCustomer(
            UUID userId,
            CreateCustomerRequest request
    );
}