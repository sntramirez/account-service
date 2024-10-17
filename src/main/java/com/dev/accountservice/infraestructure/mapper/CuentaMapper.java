package com.dev.accountservice.infraestructure.mapper;

import com.dev.accountservice.application.dto.CuentaDto;
import com.dev.accountservice.infraestructure.data.entities.Cuenta;
import com.dev.accountservice.infraestructure.data.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuentaMapper {

    @Autowired
    private CuentaRepository cuentaRepository;

    public Cuenta convertToCuenta(CuentaDto cuentaDto) {
        Cuenta cuenta = new Cuenta();
        cuenta.setTipoCuenta(cuentaDto.getTipoCuenta());
        cuenta.setEstado(cuentaDto.isEstado());
        cuenta.setSaldoInicial(cuentaDto.getSaldoInicial());
        cuenta.setClienteId(cuentaDto.getClienteId());
        if(cuentaDto.getNumeroCuenta() != null) {
            cuenta.setNumeroCuenta(cuentaDto.getNumeroCuenta());
        }else {
            cuenta.setNumeroCuenta(String.format("%010d", cuentaRepository.getNextNumeroCuentaSequenceValue()));
        }
        return cuenta;
    }

    public CuentaDto convertToCuentaDto(Cuenta cuenta) {
        return CuentaDto.builder()
                .tipoCuenta(cuenta.getTipoCuenta())
                .saldoInicial(cuenta.getSaldoInicial())
                .clienteId(cuenta.getClienteId())
                .numeroCuenta(cuenta.getNumeroCuenta())
                .build();
    }

}
