package com.axion.customer.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.axion.customer.dto.CreateCustomerRequest;
import com.axion.customer.dto.CustomerResponse;
import com.axion.customer.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
@Validated
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Creates a customer profile for the currently authenticated user.
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CustomerResponse> createCustomer(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateCustomerRequest request) {

        UUID userId = UUID.fromString(jwt.getSubject());

        CustomerResponse response =
                customerService.createCustomer(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Returns the customer profile belonging to
     * the currently authenticated user.
     */
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CustomerResponse> getCurrentCustomer(
            @AuthenticationPrincipal Jwt jwt) {

        UUID userId = UUID.fromString(jwt.getSubject());

        CustomerResponse response =
                customerService.getCustomerByUserId(userId);

        return ResponseEntity.ok(response);
    }

    /**
     * Updates the customer profile belonging to
     * the currently authenticated user.
     */
    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CustomerResponse> updateCurrentCustomer(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateCustomerRequest request) {

        UUID userId = UUID.fromString(jwt.getSubject());

        CustomerResponse response =
                customerService.updateCustomer(userId, request);

        return ResponseEntity.ok(response);
    }
}