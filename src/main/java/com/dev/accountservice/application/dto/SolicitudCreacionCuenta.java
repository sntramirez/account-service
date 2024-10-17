package com.dev.accountservice.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SolicitudCreacionCuenta {
    private Long clienteId;
    private BigDecimal saldoInicial;
    private String tipoCuenta;
}
