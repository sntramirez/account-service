package com.dev.accountservice.exceptions;

public class ValidadorLimitesException extends RuntimeException {
    public ValidadorLimitesException(String message) {
        super(message);
    }
}
