package com.dev.accountservice.application.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CuentaDto {
    @NotNull(message = "El clienteId es obligatorio")
    private Long clienteId;

    @Pattern(regexp = "^(Corriente|Ahorro)$", message = "El tipo de Cuenta debe ser 'Corriente' o 'Ahorro'")
    private String tipoCuenta;

    @NotNull(message = "El valor es obligatorio")
    private BigDecimal saldoInicial;

    private String numeroCuenta;

    private boolean estado;
}
