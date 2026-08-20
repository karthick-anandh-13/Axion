package com.axion.kyc.exception;

public class KycAlreadyExistsException extends RuntimeException {

    public KycAlreadyExistsException(String message) {
        super(message);
    }
}