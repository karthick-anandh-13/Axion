package com.axion.customer.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axion.authentication.entity.User;
import com.axion.authentication.repository.UserRepository;
import com.axion.customer.dto.CreateCustomerRequest;
import com.axion.customer.dto.CustomerResponse;
import com.axion.customer.entity.Customer;
import com.axion.customer.exception.CustomerAlreadyExistsException;
import com.axion.customer.exception.CustomerNotFoundException;
import com.axion.customer.mapper.CustomerMapper;
import com.axion.customer.repository.CustomerRepository;
import com.axion.customer.service.CustomerService;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public CustomerServiceImpl(
            CustomerRepository customerRepository,
            UserRepository userRepository) {

        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CustomerResponse createCustomer(
            UUID userId,
            CreateCustomerRequest request) {

        if (customerRepository.existsByUserId(userId)) {
            throw new CustomerAlreadyExistsException(
                    "Customer profile already exists for this user."
            );
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "User not found."
                        ));

        if (request.getNationalId() != null
                && customerRepository.existsByNationalId(
                        request.getNationalId())) {

            throw new CustomerAlreadyExistsException(
                    "A customer with this national ID already exists."
            );
        }

        if (request.getTaxIdentificationNumber() != null
                && customerRepository
                        .existsByTaxIdentificationNumber(
                                request.getTaxIdentificationNumber())) {

            throw new CustomerAlreadyExistsException(
                    "A customer with this tax identification number already exists."
            );
        }

        Customer customer =
                CustomerMapper.toEntity(request);

        customer.setUser(user);

        Customer savedCustomer =
                customerRepository.save(customer);

        return CustomerMapper.toResponse(savedCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerByUserId(UUID userId) {

        Customer customer =
                customerRepository.findByUserId(userId)
                        .orElseThrow(() ->
                                new CustomerNotFoundException(
                                        "Customer profile not found."
                                ));

        return CustomerMapper.toResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomer(
            UUID userId,
            CreateCustomerRequest request) {

        Customer customer =
                customerRepository.findByUserId(userId)
                        .orElseThrow(() ->
                                new CustomerNotFoundException(
                                        "Customer profile not found."
                                ));

        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setDateOfBirth(request.getDateOfBirth());

        if (request.getNationalId() != null
                && !request.getNationalId()
                        .equals(customer.getNationalId())
                && customerRepository.existsByNationalId(
                        request.getNationalId())) {

            throw new CustomerAlreadyExistsException(
                    "A customer with this national ID already exists."
            );
        }

        if (request.getTaxIdentificationNumber() != null
                && !request.getTaxIdentificationNumber()
                        .equals(
                                customer.getTaxIdentificationNumber()
                        )
                && customerRepository
                        .existsByTaxIdentificationNumber(
                                request.getTaxIdentificationNumber())) {

            throw new CustomerAlreadyExistsException(
                    "A customer with this tax identification number already exists."
            );
        }

        customer.setNationalId(request.getNationalId());
        customer.setTaxIdentificationNumber(
                request.getTaxIdentificationNumber()
        );

        Customer updatedCustomer =
                customerRepository.save(customer);

        return CustomerMapper.toResponse(updatedCustomer);
    }
}