package com.axion.customer.mapper;
import com.axion.customer.dto.CreateCustomerRequest;
import com.axion.customer.dto.CustomerResponse;
import com.axion.customer.entity.Customer;
public final class CustomerMapper {
 private CustomerMapper() {}
 public static Customer toEntity(CreateCustomerRequest r) { Customer c = new Customer(); c.setFirstName(r.getFirstName()); c.setLastName(r.getLastName()); c.setDateOfBirth(r.getDateOfBirth()); c.setNationalId(r.getNationalId()); c.setTaxIdentificationNumber(r.getTaxIdentificationNumber()); return c; }
 public static CustomerResponse toResponse(Customer c) { CustomerResponse r = new CustomerResponse(); r.setId(c.getId()); if (c.getUser() != null) r.setUserId(c.getUser().getId()); r.setFirstName(c.getFirstName()); r.setLastName(c.getLastName()); r.setDateOfBirth(c.getDateOfBirth()); r.setNationalId(c.getNationalId()); r.setTaxIdentificationNumber(c.getTaxIdentificationNumber()); r.setStatus(c.getStatus()); r.setCreatedAt(c.getCreatedAt()); r.setUpdatedAt(c.getUpdatedAt()); return r; }
}
