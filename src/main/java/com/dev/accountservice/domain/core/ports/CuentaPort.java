package com.dev.accountservice.domain.core.ports;

import com.dev.accountservice.application.dto.CuentaDto;
import com.dev.accountservice.infraestructure.data.entities.Cuenta;
import org.springframework.stereotype.Service;

@Service
public interface CuentaPort {
    Cuenta crearCuenta(Cuenta cuentaDto);
}
