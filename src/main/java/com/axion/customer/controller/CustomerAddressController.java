package com.axion.customer.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axion.customer.dto.AddressResponse;
import com.axion.customer.dto.CreateAddressRequest;
import com.axion.customer.dto.UpdateAddressRequest;
import com.axion.customer.service.CustomerAddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers/me/addresses")
@Validated
public class CustomerAddressController {

    private final CustomerAddressService customerAddressService;

    public CustomerAddressController(
            CustomerAddressService customerAddressService) {

        this.customerAddressService = customerAddressService;
    }

    /**
     * Create an address for the authenticated customer.
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AddressResponse> createAddress(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateAddressRequest request) {

        UUID userId = getUserId(jwt);

        AddressResponse response =
                customerAddressService.createAddress(
                        userId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Get all addresses belonging to the authenticated customer.
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<AddressResponse>> getAddresses(
            @AuthenticationPrincipal Jwt jwt) {

        UUID userId = getUserId(jwt);

        List<AddressResponse> response =
                customerAddressService.getAddresses(userId);

        return ResponseEntity.ok(response);
    }

    /**
     * Get one address belonging to the authenticated customer.
     */
    @GetMapping("/{addressId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AddressResponse> getAddress(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID addressId) {

        UUID userId = getUserId(jwt);

        AddressResponse response =
                customerAddressService.getAddress(
                        userId,
                        addressId
                );

        return ResponseEntity.ok(response);
    }

    /**
     * Update an address belonging to the authenticated customer.
     */
    @PutMapping("/{addressId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AddressResponse> updateAddress(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID addressId,
            @Valid @RequestBody UpdateAddressRequest request) {

        UUID userId = getUserId(jwt);

        AddressResponse response =
                customerAddressService.updateAddress(
                        userId,
                        addressId,
                        request
                );

        return ResponseEntity.ok(response);
    }

    /**
     * Delete an address belonging to the authenticated customer.
     */
    @DeleteMapping("/{addressId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteAddress(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID addressId) {

        UUID userId = getUserId(jwt);

        customerAddressService.deleteAddress(
                userId,
                addressId
        );

        return ResponseEntity.noContent().build();
    }

    /**
     * Extract authenticated user's UUID from JWT subject.
     */
    private UUID getUserId(Jwt jwt) {

        return UUID.fromString(jwt.getSubject());
    }
}