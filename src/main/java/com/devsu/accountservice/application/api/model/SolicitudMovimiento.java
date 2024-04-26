package com.devsu.accountservice.application.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SolicitudMovimiento {
    private String numeroCuenta;
    private String tipoMovimiento;
    private BigDecimal valor;
}
