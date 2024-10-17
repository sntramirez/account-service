package com.dev.accountservice.application.services;

import com.dev.accountservice.application.dto.MovimientoDto;
import com.dev.accountservice.application.dto.MovimientoRequestDto;
import com.dev.accountservice.application.dto.RespuestaMovimiento;
import com.dev.accountservice.domain.core.MovimientoService;
import com.dev.accountservice.domain.core.ports.MovimientoPort;
import com.dev.accountservice.infraestructure.data.entities.Movimiento;
import com.dev.accountservice.infraestructure.mapper.MovimientoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimientoApplicationService {
    @Autowired
    MovimientoMapper movimientoMapper;

    private final MovimientoService movimientoService;


    @Autowired
    public MovimientoApplicationService(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    public MovimientoDto crearMovimento(MovimientoRequestDto movimientoDto) {
        Movimiento movimiento = movimientoService.realizarMovimiento(movimientoDto.getNumeroCuenta(), movimientoDto.getValor(), movimientoDto.getTipoMovimiento());
        //return movimientoPort.crearMovimento(movimientoDto);
        return movimientoMapper.convertToMovimientoDto(movimiento);
    }
}
