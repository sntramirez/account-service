package com.dev.accountservice.domain.core.ports;

import com.dev.accountservice.domain.core.model.MovimientoDto;
import com.dev.accountservice.domain.core.model.RespuestaMovimiento;
import org.springframework.stereotype.Service;

@Service
public interface MovimientoPort {
    RespuestaMovimiento crearMovimento(MovimientoDto movimientoDto);
}
