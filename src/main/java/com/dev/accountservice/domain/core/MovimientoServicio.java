package com.dev.accountservice.domain.core;

import com.dev.accountservice.domain.core.model.MovimientoDto;
import com.dev.accountservice.domain.core.model.RespuestaMovimiento;
import com.dev.accountservice.domain.core.model.SaldoNoDisponibleException;
import com.dev.accountservice.infraestructure.data.entities.Cuenta;
import com.dev.accountservice.infraestructure.data.entities.Movimiento;
import com.dev.accountservice.infraestructure.data.repository.CuentaRepository;
import com.dev.accountservice.infraestructure.data.repository.MovimientoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class MovimientoServicio {

    private static final Logger log = LoggerFactory.getLogger(MovimientoServicio.class);

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;


    public RespuestaMovimiento crearMovimento(MovimientoDto movimientoDto) {

        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(movimientoDto.getNumeroCuenta());
        List<Movimiento> movimientos = movimientoRepository.findByCuenta(cuenta.getId());
        BigDecimal nuevoSaldo = BigDecimal.ZERO;
        if(movimientos.isEmpty()){
            nuevoSaldo = cuenta.getSaldoInicial().add( movimientoDto.getValor() );
        }else {
            nuevoSaldo = movimientoDto.getValor().add(movimientos.get(0).getSaldo());
        }

        RespuestaMovimiento respuesta = new RespuestaMovimiento();
        respuesta.setNumeroCuenta(movimientoDto.getNumeroCuenta());

        if (nuevoSaldo.compareTo(BigDecimal.ZERO ) < 0) {
            // Saldo insuficiente
            respuesta.setExito(false);
            respuesta.setMensaje("Saldo no disponible");
            throw new SaldoNoDisponibleException("Saldo no disponible para realizar el retiro");
        } else {
            // Procesar el movimiento
            Movimiento movimiento = new Movimiento();
            movimiento.setCuenta(cuenta);
            movimiento.setTipoMovimiento(movimientoDto.getTipoMovimiento());
            movimiento.setValor(movimientoDto.getValor());
            movimiento.setSaldo(nuevoSaldo);
            //cuenta.setSaldoInicial(nuevoSaldo);
            cuentaRepository.save(cuenta);

            movimiento.setFecha(LocalDate.now());
            movimientoRepository.save(movimiento);

            respuesta.setExito(true);
            respuesta.setMensaje("Movimiento realizado con éxito");
        }

        return respuesta;
    }
}
