package com.dev.accountservice.application.services;

import com.dev.accountservice.domain.core.model.MovimientoDto;
import com.dev.accountservice.domain.core.model.RespuestaMovimiento;
import com.dev.accountservice.domain.core.ports.MovimientoPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimientoApplicationService {

    @Autowired
    MovimientoPort movimientoPort;

    public RespuestaMovimiento crearMovimento(MovimientoDto movimientoDto) {
        return movimientoPort.crearMovimento(movimientoDto);
    }
}
