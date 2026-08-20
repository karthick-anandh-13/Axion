package com.axion.kyc.exception;

public class KycDocumentNotFoundException
        extends RuntimeException {

    public KycDocumentNotFoundException(String message) {
        super(message);
    }
}