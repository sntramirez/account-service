package com.dev.accountservice.domain.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class MovimientoDto {

    @NotBlank(message = "El número de cuenta es obligatorio")
    private String numeroCuenta;
    private LocalDate fecha;
    @Pattern(regexp = "^(Retiro|Deposito)$", message = "El tipo de movimiento debe ser 'Retiro' o 'Deposito'")
    private String tipoMovimiento;
    @NotNull(message = "El valor es obligatorio")
    private BigDecimal valor;
    private BigDecimal saldo;
}
