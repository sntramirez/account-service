package com.dev.accountservice.domain.core;

import com.dev.accountservice.application.dto.CuentaDto;
import com.dev.accountservice.application.dto.RespuestaMovimiento;
import com.dev.accountservice.application.dto.SolicitudCreacionCuenta;
import com.dev.accountservice.domain.core.ports.CuentaPort;
import com.dev.accountservice.infraestructure.data.entities.Cuenta;
import com.dev.accountservice.infraestructure.mapper.CuentaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CuentaServicio {


    private static final Logger log = LoggerFactory.getLogger(CuentaServicio.class);

    @Autowired
    private CuentaPort cuentaPort;


    @Autowired
    private KafkaTemplate<String, RespuestaMovimiento> respuestaKafkaTemplate;

    @Autowired
    private CuentaMapper cuentaMapper;


    public CuentaDto crearCuenta(CuentaDto cuentaDto) {
        Cuenta cuenta = cuentaPort.crearCuenta(cuentaMapper.convertToCuenta(cuentaDto));
        return cuentaMapper.convertToCuentaDto(cuenta);
    }


    @KafkaListener(topics = "topic-solicitudes-creacion-cuenta", groupId = "grupo-cuentas")
    public void procesarSolicitudCreacionCuenta(SolicitudCreacionCuenta solicitud) {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setSaldoInicial(solicitud.getSaldoInicial());
        cuentaDto.setTipoCuenta(solicitud.getTipoCuenta());
        cuentaDto.setClienteId(solicitud.getClienteId());
        cuentaDto.setEstado(true);

        Cuenta cuenta = cuentaPort.crearCuenta(cuentaMapper.convertToCuenta(cuentaDto));

        log.info("Numero de cuenta: ", cuenta.getNumeroCuenta());
    }


}
