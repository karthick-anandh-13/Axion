package com.axion.payment.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.axion.payment.entity.Payment;

public interface PaymentRepository
        extends JpaRepository<Payment, UUID> {
}