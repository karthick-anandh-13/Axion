package com.axion.customer.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axion.customer.dto.AddressResponse;
import com.axion.customer.dto.CreateAddressRequest;
import com.axion.customer.dto.UpdateAddressRequest;
import com.axion.customer.entity.Customer;
import com.axion.customer.entity.CustomerAddress;
import com.axion.customer.exception.AddressNotFoundException;
import com.axion.customer.exception.CustomerNotFoundException;
import com.axion.customer.mapper.CustomerAddressMapper;
import com.axion.customer.repository.CustomerAddressRepository;
import com.axion.customer.repository.CustomerRepository;
import com.axion.customer.service.CustomerAddressService;

@Service
@Transactional
public class CustomerAddressServiceImpl
        implements CustomerAddressService {

    private final CustomerRepository customerRepository;
    private final CustomerAddressRepository addressRepository;

    public CustomerAddressServiceImpl(
            CustomerRepository customerRepository,
            CustomerAddressRepository addressRepository) {

        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public AddressResponse createAddress(
            UUID userId,
            CreateAddressRequest request) {

        Customer customer = findCustomerByUserId(userId);

        CustomerAddress address =
                CustomerAddressMapper.toEntity(request);

        address.setCustomer(customer);

        /*
         * If this address is marked as primary,
         * remove the primary flag from existing addresses.
         */
        if (request.isPrimaryAddress()) {
            makeExistingAddressesNonPrimary(customer.getId());
        }

        CustomerAddress savedAddress =
                addressRepository.save(address);

        return CustomerAddressMapper.toResponse(savedAddress);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressResponse> getAddresses(UUID userId) {

        Customer customer = findCustomerByUserId(userId);

        return addressRepository
                .findByCustomerId(customer.getId())
                .stream()
                .map(CustomerAddressMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AddressResponse getAddress(
            UUID userId,
            UUID addressId) {

        Customer customer = findCustomerByUserId(userId);

        CustomerAddress address =
                findAddressForCustomer(
                        customer.getId(),
                        addressId
                );

        return CustomerAddressMapper.toResponse(address);
    }

    @Override
    public AddressResponse updateAddress(
            UUID userId,
            UUID addressId,
            UpdateAddressRequest request) {

        Customer customer = findCustomerByUserId(userId);

        CustomerAddress address =
                findAddressForCustomer(
                        customer.getId(),
                        addressId
                );

        if (request.isPrimaryAddress()) {
            makeExistingAddressesNonPrimary(
                    customer.getId()
            );
        }

        address.setType(request.getType());
        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPostalCode(request.getPostalCode());
        address.setCountry(request.getCountry());
        address.setPrimaryAddress(
                request.isPrimaryAddress()
        );

        CustomerAddress updatedAddress =
                addressRepository.save(address);

        return CustomerAddressMapper.toResponse(updatedAddress);
    }

    @Override
    public void deleteAddress(
            UUID userId,
            UUID addressId) {

        Customer customer = findCustomerByUserId(userId);

        CustomerAddress address =
                findAddressForCustomer(
                        customer.getId(),
                        addressId
                );

        addressRepository.delete(Objects.requireNonNull(address));
    }

    private Customer findCustomerByUserId(UUID userId) {

        return customerRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer profile not found."
                        ));
    }

    private CustomerAddress findAddressForCustomer(
            UUID customerId,
            UUID addressId) {

        return addressRepository.findById(addressId)
                .filter(address ->
                        address.getCustomer()
                                .getId()
                                .equals(customerId))
                .orElseThrow(() ->
                        new AddressNotFoundException(
                                "Address not found."
                        ));
    }

    private void makeExistingAddressesNonPrimary(
            UUID customerId) {

        List<CustomerAddress> addresses =
                addressRepository.findByCustomerId(customerId);

        addresses.forEach(address ->
                address.setPrimaryAddress(false));
    }
}