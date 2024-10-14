package com.dev.accountservice.domain.core.ports;

import com.dev.accountservice.domain.core.model.CuentaDto;
import org.springframework.stereotype.Service;

@Service
public interface CuentaPort {
    CuentaDto crearCuenta(CuentaDto cuentaDto);
}
