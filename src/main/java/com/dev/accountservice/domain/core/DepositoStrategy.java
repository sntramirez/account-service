package com.dev.accountservice.domain.core;

import com.dev.accountservice.exceptions.SaldoNoDisponibleException;
import com.dev.accountservice.infraestructure.data.entities.Cuenta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DepositoStrategy implements OperacionStrategy  {
    @Override
    public void realizarOperacion(Cuenta cuenta, BigDecimal monto) {
        if (monto.compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoNoDisponibleException("Valor no valido para deposito");
        }
        cuenta.setSaldoInicial(cuenta.getSaldoInicial().add(monto));
    }

    @Override
    public void deshacerOperacion(Cuenta cuenta, BigDecimal monto) {
        cuenta.setSaldoInicial(cuenta.getSaldoInicial().subtract(monto));
    }

    @Override
    public String generarClaveIdempotencia(Cuenta cuenta, BigDecimal monto, LocalDateTime timestamp) {
        return "DEPOSITO_" + cuenta.getNumeroCuenta() + "_" + monto.toString() + "_" + timestamp.toString().substring(0,16);
    }
}
