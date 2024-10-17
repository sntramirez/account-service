package com.dev.accountservice.infraestructure.mapper;

import com.dev.accountservice.application.dto.MovimientoDto;
import com.dev.accountservice.infraestructure.data.entities.Movimiento;
import org.springframework.stereotype.Service;

@Service
public class MovimientoMapper {

    public MovimientoDto convertToMovimientoDto(Movimiento movimiento) {
        return MovimientoDto.builder()
                .id(movimiento.getId())
                .tipoMovimiento(movimiento.getTipoMovimiento())
                .fecha(movimiento.getFecha())
                .monto(movimiento.getValor())
                .numeroCuenta(movimiento.getCuenta().getNumeroCuenta())
                .build();
    }

}
