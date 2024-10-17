package com.dev.accountservice.domain.core;

import com.dev.accountservice.infraestructure.data.entities.Cuenta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface OperacionStrategy {
    void realizarOperacion(Cuenta cuenta, BigDecimal monto);
    void deshacerOperacion(Cuenta cuenta, BigDecimal monto);
    String generarClaveIdempotencia(Cuenta cuenta, BigDecimal monto, LocalDateTime timestamp);
}
