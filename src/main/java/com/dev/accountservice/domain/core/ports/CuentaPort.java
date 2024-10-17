package com.dev.accountservice.domain.core.ports;

import com.dev.accountservice.application.dto.CuentaDto;
import com.dev.accountservice.infraestructure.data.entities.Cuenta;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CuentaPort {
    Cuenta crearCuenta(Cuenta cuenta);
    Optional<Cuenta> findByNumeroCuenta(@Param("numeroCuenta") String numeroCuenta);
}
