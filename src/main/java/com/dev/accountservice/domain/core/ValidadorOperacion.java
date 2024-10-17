package com.dev.accountservice.domain.core;

import com.dev.accountservice.infraestructure.data.entities.Movimiento;

public abstract class ValidadorOperacion {
    protected ValidadorOperacion siguiente;

    public void setSiguiente(ValidadorOperacion siguiente) {
        this.siguiente = siguiente;
    }

    public abstract void validar(Movimiento movimiento);
}
