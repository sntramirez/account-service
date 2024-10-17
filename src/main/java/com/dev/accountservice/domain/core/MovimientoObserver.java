package com.dev.accountservice.domain.core;

import com.dev.accountservice.infraestructure.data.entities.Movimiento;

public interface MovimientoObserver {
    void notificar(Movimiento movimiento);
}
