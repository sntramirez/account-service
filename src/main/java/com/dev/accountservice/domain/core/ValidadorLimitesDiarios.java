package com.dev.accountservice.domain.core;

import com.dev.accountservice.application.dto.TipoMovimiento;
import com.dev.accountservice.exceptions.ValidadorLimitesException;
import com.dev.accountservice.infraestructure.data.entities.Movimiento;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ValidadorLimitesDiarios extends ValidadorOperacion {
    @Override
    public void validar(Movimiento movimiento) {
        if (TipoMovimiento.RETIRO.name().equals(movimiento.getTipoMovimiento()) &&
                movimiento.getValor().compareTo(BigDecimal.valueOf(500)) > 0) {
            throw new ValidadorLimitesException("Excede el limite diario para retirar");
        }
        if (siguiente != null) {
            siguiente.validar(movimiento);
        }

    }

}
