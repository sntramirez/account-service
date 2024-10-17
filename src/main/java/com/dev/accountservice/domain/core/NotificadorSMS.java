package com.dev.accountservice.domain.core;

import com.dev.accountservice.infraestructure.data.entities.Movimiento;
import org.springframework.stereotype.Component;

@Component
public class NotificadorSMS implements MovimientoObserver {
    @Override
    public void notificar(Movimiento movimiento) {

    }
    // Implementación similar a NotificadorEmail
}
