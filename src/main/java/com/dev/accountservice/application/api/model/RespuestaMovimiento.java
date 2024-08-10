package com.dev.accountservice.application.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespuestaMovimiento {
    private String numeroCuenta;
    private boolean exito;
    private String mensaje;
}
