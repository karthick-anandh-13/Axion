package com.axion.customer.mapper;

import com.axion.customer.dto.AddressResponse;
import com.axion.customer.dto.CreateAddressRequest;
import com.axion.customer.entity.CustomerAddress;

public final class CustomerAddressMapper {

    private CustomerAddressMapper() {
        // Prevent instantiation
    }

    public static CustomerAddress toEntity(
            CreateAddressRequest request) {

        CustomerAddress address = new CustomerAddress();

        address.setType(request.getType());
        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPostalCode(request.getPostalCode());
        address.setCountry(request.getCountry());
        address.setPrimaryAddress(request.isPrimaryAddress());

        return address;
    }

    public static AddressResponse toResponse(
            CustomerAddress address) {

        AddressResponse response = new AddressResponse();

        response.setId(address.getId());
        response.setType(address.getType());
        response.setAddressLine1(address.getAddressLine1());
        response.setAddressLine2(address.getAddressLine2());
        response.setCity(address.getCity());
        response.setState(address.getState());
        response.setPostalCode(address.getPostalCode());
        response.setCountry(address.getCountry());
        response.setPrimaryAddress(address.isPrimaryAddress());
        response.setCreatedAt(address.getCreatedAt());
        response.setUpdatedAt(address.getUpdatedAt());

        return response;
    }
}