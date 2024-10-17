package com.dev.accountservice.application.services;

import com.dev.accountservice.application.dto.CuentaDto;
import com.dev.accountservice.domain.core.CuentaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuentaApplicationService {

    @Autowired
    CuentaServicio cuentaServicio;

    public CuentaDto crearCuenta(CuentaDto cuentaDto) {
        return cuentaServicio.crearCuenta(cuentaDto );
    }
}
