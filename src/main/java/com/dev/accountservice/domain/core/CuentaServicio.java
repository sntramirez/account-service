package com.dev.accountservice.domain.core;

import com.dev.accountservice.domain.core.mapper.CuentaMapper;
import com.dev.accountservice.domain.core.model.CuentaDto;
import com.dev.accountservice.domain.core.model.RespuestaMovimiento;
import com.dev.accountservice.domain.core.model.SolicitudCreacionCuenta;
import com.dev.accountservice.domain.core.ports.CuentaPort;
import com.dev.accountservice.infraestructure.data.entities.Cuenta;
import com.dev.accountservice.infraestructure.data.repository.CuentaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CuentaServicio implements CuentaPort {


    private static final Logger log = LoggerFactory.getLogger(CuentaServicio.class);

    @Autowired
    private CuentaRepository cuentaRepository;


    @Autowired
    private KafkaTemplate<String, RespuestaMovimiento> respuestaKafkaTemplate;

    @Autowired
    private CuentaMapper cuentaMapper;


    @Override
    public CuentaDto crearCuenta(CuentaDto cuentaDto) {
        Cuenta cuenta = cuentaRepository.save(cuentaMapper.convertToCuenta(cuentaDto));
        return cuentaMapper.convertToCuentaDto(cuenta);
    }


    @KafkaListener(topics = "topic-solicitudes-creacion-cuenta", groupId = "grupo-cuentas")
    public void procesarSolicitudCreacionCuenta(SolicitudCreacionCuenta solicitud) {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setSaldoInicial(solicitud.getSaldoInicial());
        cuentaDto.setTipoCuenta(solicitud.getTipoCuenta());
        cuentaDto.setClienteId(solicitud.getClienteId());
        cuentaDto.setEstado(true);

        Cuenta cuenta = cuentaRepository.save(cuentaMapper.convertToCuenta(cuentaDto));

        log.info("Numero de cuenta: ", cuenta.getNumeroCuenta());
    }


}
