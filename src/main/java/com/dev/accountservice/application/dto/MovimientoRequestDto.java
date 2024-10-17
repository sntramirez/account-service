package com.dev.accountservice.application.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class MovimientoRequestDto {

    @NotBlank(message = "Campo obligatorio")
    private String numeroCuenta;
    private LocalDate fecha;
    @Pattern(regexp = "^(RETIRO|DEPOSITO)$", message = "El tipo de movimiento debe ser 'RETIRO' o 'DEPOSITO'")
    private String tipoMovimiento;
    @NotNull(message = "Campo obligatorio")
    private BigDecimal valor;
    private BigDecimal saldo;

}
