package com.axion.customer.service;

import java.util.List;
import java.util.UUID;

import com.axion.customer.dto.AddressResponse;
import com.axion.customer.dto.CreateAddressRequest;
import com.axion.customer.dto.UpdateAddressRequest;

public interface CustomerAddressService {

    AddressResponse createAddress(
            UUID userId,
            CreateAddressRequest request
    );

    List<AddressResponse> getAddresses(UUID userId);

    AddressResponse getAddress(
            UUID userId,
            UUID addressId
    );

    AddressResponse updateAddress(
            UUID userId,
            UUID addressId,
            UpdateAddressRequest request
    );

    void deleteAddress(
            UUID userId,
            UUID addressId
    );
}