package com.dev.accountservice.domain.core.ports;

import com.dev.accountservice.application.dto.MovimientoDto;
import com.dev.accountservice.application.dto.RespuestaMovimiento;
import org.springframework.stereotype.Service;

@Service
public interface MovimientoPort {
    RespuestaMovimiento crearMovimento(MovimientoDto movimientoDto);
}
