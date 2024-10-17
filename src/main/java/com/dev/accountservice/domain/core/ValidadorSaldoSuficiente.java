package com.dev.accountservice.domain.core;

import com.dev.accountservice.application.dto.TipoMovimiento;
import com.dev.accountservice.exceptions.SaldoNoDisponibleException;
import com.dev.accountservice.infraestructure.data.entities.Movimiento;
import org.springframework.stereotype.Component;

@Component
public class ValidadorSaldoSuficiente extends ValidadorOperacion {
    @Override
    public void validar(Movimiento movimiento) {
        if (TipoMovimiento.RETIRO.name().equals(movimiento.getTipoMovimiento()) &&
                movimiento.getCuenta().getSaldoInicial().compareTo(movimiento.getValor()) < 0) {
            throw new SaldoNoDisponibleException("Saldo insuficiente para realizar el retiro");
        }
        if (siguiente != null) {
            siguiente.validar(movimiento);
        }
    }
}
