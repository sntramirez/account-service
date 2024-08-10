package com.dev.accountservice.domain.core;

import com.dev.accountservice.application.api.model.RespuestaMovimiento;
import com.dev.accountservice.application.api.model.SolicitudMovimiento;
import com.dev.accountservice.infraestructure.data.entities.Cuenta;
import com.dev.accountservice.infraestructure.data.entities.Movimiento;
import com.dev.accountservice.infraestructure.data.repository.CuentaRepository;
import com.dev.accountservice.infraestructure.data.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class CuentaServicio {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private KafkaTemplate<String, RespuestaMovimiento> respuestaKafkaTemplate;

    @KafkaListener(topics = "topic-solicitudes-movimiento", groupId = "grupo-cuentas")
    public void procesarSolicitudMovimiento(SolicitudMovimiento solicitud) {

        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(solicitud.getNumeroCuenta());
        List<Movimiento> movimientos = movimientoRepository.findByCuenta(cuenta.getId());
        BigDecimal nuevoSaldo = BigDecimal.ZERO;
        if(movimientos.isEmpty()){
            nuevoSaldo = cuenta.getSaldoInicial().add( solicitud.getValor() );
        }else {
            nuevoSaldo = solicitud.getValor().add(movimientos.get(0).getSaldo());
        }

        RespuestaMovimiento respuesta = new RespuestaMovimiento();
        respuesta.setNumeroCuenta(solicitud.getNumeroCuenta());

        if (nuevoSaldo.compareTo(BigDecimal.ZERO ) < 0) {
            // Saldo insuficiente
            respuesta.setExito(false);
            respuesta.setMensaje("Saldo no disponible");
        } else {
            // Procesar el movimiento
            Movimiento movimiento = new Movimiento();
            movimiento.setCuenta(cuenta);
            movimiento.setTipoMovimiento(solicitud.getTipoMovimiento());
            movimiento.setValor(solicitud.getValor());
            movimiento.setSaldo(nuevoSaldo);
            //cuenta.setSaldoInicial(nuevoSaldo);
            cuentaRepository.save(cuenta);

            movimiento.setFecha(LocalDate.now());
            movimientoRepository.save(movimiento);

            respuesta.setExito(true);
            respuesta.setMensaje("Movimiento realizado con éxito");
        }

        // Enviar respuesta a Kafka
        respuestaKafkaTemplate.send("topic-respuestas-movimiento", respuesta);
    }
}
