package com.dev.accountservice.domain.core;

import com.dev.accountservice.exceptions.SaldoNoDisponibleException;
import com.dev.accountservice.infraestructure.data.entities.Cuenta;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class RetiroStrategy implements OperacionStrategy {
    @Override
    public void realizarOperacion(Cuenta cuenta, BigDecimal monto) {
        if (cuenta.getSaldoInicial().compareTo(monto) < 0) {
            throw new SaldoNoDisponibleException("Saldo insuficiente para realizar el retiro");
        }
        cuenta.setSaldoInicial(cuenta.getSaldoInicial().subtract(monto));
    }

    @Override
    public void deshacerOperacion(Cuenta cuenta, BigDecimal monto) {
        cuenta.setSaldoInicial(cuenta.getSaldoInicial().add(monto));
    }

    @Override
    public String generarClaveIdempotencia(Cuenta cuenta, BigDecimal monto, LocalDateTime timestamp) {
        return "RETIRO_" + cuenta.getNumeroCuenta() + "_" + monto.toString() + "_" + timestamp.toString().substring(0,16);
    }
}
