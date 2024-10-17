package com.dev.accountservice.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class MovimientoDto {
    private Long id;
    private String numeroCuenta;
    private BigDecimal monto;
    private String tipoMovimiento;
    private LocalDate fecha;
}
