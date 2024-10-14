package com.dev.accountservice.exceptions;

public class CuentaNoDisponibleException extends RuntimeException {
    public CuentaNoDisponibleException(String message) {
        super(message);
    }
}
