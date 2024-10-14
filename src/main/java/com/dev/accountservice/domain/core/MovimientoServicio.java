package com.dev.accountservice.domain.core;

import com.dev.accountservice.domain.core.model.MovimientoDto;
import com.dev.accountservice.domain.core.model.RespuestaMovimiento;
import com.dev.accountservice.exceptions.CuentaNoDisponibleException;
import com.dev.accountservice.exceptions.SaldoNoDisponibleException;
import com.dev.accountservice.domain.core.ports.MovimientoPort;
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
import java.util.Optional;

@Service
public class MovimientoServicio implements MovimientoPort {

    private static final Logger log = LoggerFactory.getLogger(MovimientoServicio.class);

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Override
    public RespuestaMovimiento crearMovimento(MovimientoDto movimientoDto) {

        RespuestaMovimiento respuesta = new RespuestaMovimiento();
        respuesta.setNumeroCuenta(movimientoDto.getNumeroCuenta());
        respuesta.setExito(true);
        respuesta.setMensaje("Movimiento realizado con éxito");

        // Verificar si ya existe una transacción con el mismo idempotencyKey
        Optional<Movimiento> existingTransaction = movimientoRepository.findByIdempotenciaClave(movimientoDto.getIdempotenciaClave());

        if (existingTransaction.isPresent()) {
            return respuesta;
        }

        Optional <Cuenta> cuentaOpcional = cuentaRepository.findByNumeroCuenta(movimientoDto.getNumeroCuenta());
        if (!cuentaOpcional.isPresent()){
            throw new CuentaNoDisponibleException("Cuenta no disponible");
        }
        Cuenta cuenta = cuentaOpcional.get();

        List<Movimiento> movimientos = movimientoRepository.findByCuenta(cuenta.getId());
        BigDecimal nuevoSaldo;
        if(movimientos.isEmpty()){
            nuevoSaldo = cuenta.getSaldoInicial().add( movimientoDto.getValor() );
        }else {
            nuevoSaldo = movimientoDto.getValor().add(movimientos.get(0).getSaldo());
        }

        if (nuevoSaldo.compareTo(BigDecimal.ZERO ) < 0) {
            throw new SaldoNoDisponibleException("Saldo no disponible para realizar el retiro");
        } else {
            // Procesar el movimiento
            Movimiento movimiento = new Movimiento();
            movimiento.setCuenta(cuenta);
            movimiento.setTipoMovimiento(movimientoDto.getTipoMovimiento());
            movimiento.setValor(movimientoDto.getValor());
            movimiento.setSaldo(nuevoSaldo);
            cuentaRepository.save(cuenta);
            movimiento.setFecha(LocalDate.now());
            movimiento.setIdempotenciaClave(movimientoDto.getIdempotenciaClave());
            movimientoRepository.save(movimiento);
        }

        return respuesta;
    }
}
