package com.devsu.accountservice.infraestructure.data.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@SequenceGenerator(name = "default_gen", sequenceName = "cuenta_id_seq" , allocationSize = 1)
@Table(name = "Cuentas")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_cuenta" , nullable = false)
    private String numeroCuenta;

    @Column(name = "tipo_cuenta" , nullable = false)
    private String tipoCuenta;

    @Column(name = "saldo_inicial")
    private BigDecimal saldoInicial;

    @Column(name = "estado", nullable = false)
    private boolean estado;

}
