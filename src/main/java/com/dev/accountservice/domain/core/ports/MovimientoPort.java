package com.dev.accountservice.domain.core.ports;

import com.dev.accountservice.application.dto.MovimientoRequestDto;
import com.dev.accountservice.application.dto.RespuestaMovimiento;
import com.dev.accountservice.infraestructure.data.entities.Movimiento;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface MovimientoPort {
    Movimiento crearMovimento (Movimiento Movimiento);
    Optional<Movimiento> findByIdempotenciaClave(String idempotenciaClave);
}
