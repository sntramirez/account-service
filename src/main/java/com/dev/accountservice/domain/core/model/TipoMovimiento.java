package com.dev.accountservice.domain.core.model;

public enum TipoMovimiento {
    RETIRO, DEPOSITO;

    public static boolean isValid(String tipo) {
        return tipo != null && (tipo.equalsIgnoreCase("Retiro") || tipo.equalsIgnoreCase("Deposito"));
    }
}