package com.dev.accountservice.exceptions;

public class TipoMovimientoInvalidoException extends RuntimeException {
    public TipoMovimientoInvalidoException(String message) {
        super(message);
    }
}
