package com.dev.accountservice.exceptions;

public class OperacionDuplicadaException extends RuntimeException {
    public OperacionDuplicadaException(String message) {
        super(message);
    }
}
