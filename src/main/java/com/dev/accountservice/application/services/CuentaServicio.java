package com.dev.accountservice.application.services;

import com.dev.accountservice.domain.core.model.CuentaDto;
import com.dev.accountservice.domain.core.ports.CuentaPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuentaServicio {

    @Autowired
    CuentaPort cuentaPort;

    public CuentaDto crearCuenta(CuentaDto cuentaDto) {
        return cuentaPort.crearCuenta(cuentaDto );
    }
}
