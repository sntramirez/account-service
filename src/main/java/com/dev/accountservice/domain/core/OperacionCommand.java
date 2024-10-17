package com.dev.accountservice.domain.core;

import com.dev.accountservice.infraestructure.data.entities.Cuenta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OperacionCommand {
    private Cuenta cuenta;
    private BigDecimal monto;
    private OperacionStrategy estrategia;
    private String claveIdempotencia;

    public OperacionCommand(Cuenta cuenta, BigDecimal monto, OperacionStrategy estrategia, LocalDateTime timestamp) {
        this.cuenta = cuenta;
        this.monto = monto;
        this.estrategia = estrategia;
        this.claveIdempotencia = estrategia.generarClaveIdempotencia(cuenta, monto, timestamp);
    }

    public void ejecutar() {
        estrategia.realizarOperacion(cuenta, monto);
    }

    public void deshacer() {
        estrategia.deshacerOperacion(cuenta, monto);
    }

    public String getClaveIdempotencia() {
        return claveIdempotencia;
    }
}
