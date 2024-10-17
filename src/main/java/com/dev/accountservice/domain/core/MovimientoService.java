package com.dev.accountservice.domain.core;

import com.dev.accountservice.application.dto.TipoMovimiento;
import com.dev.accountservice.domain.core.ports.CuentaPort;
import com.dev.accountservice.domain.core.ports.MovimientoPort;
import com.dev.accountservice.exceptions.CuentaNoDisponibleException;
import com.dev.accountservice.exceptions.OperacionDuplicadaException;
import com.dev.accountservice.exceptions.TipoMovimientoInvalidoException;
import com.dev.accountservice.infraestructure.data.entities.Cuenta;
import com.dev.accountservice.infraestructure.data.entities.Movimiento;
import com.dev.accountservice.infraestructure.data.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MovimientoService {

    private final MovimientoPort movimientoPort;
    private final CuentaPort cuentaPort;
    private final List<MovimientoObserver> observadores = new ArrayList<>();
    private final ValidadorOperacion validadorCadena;
    private final Map<String, OperacionStrategy> estrategias;

    @Autowired
    public MovimientoService(MovimientoPort movimientoPort,
                             CuentaPort cuentaPort) {
        this.movimientoPort = movimientoPort;
        this.cuentaPort = cuentaPort;

        // Configurar Chain of Responsibility
        ValidadorSaldoSuficiente validadorSaldo = new ValidadorSaldoSuficiente();
        ValidadorLimitesDiarios validadorLimites = new ValidadorLimitesDiarios();
        validadorSaldo.setSiguiente(validadorLimites);
        this.validadorCadena = validadorSaldo;

        // Configurar Observers
        observadores.add(new NotificadorEmail());
        observadores.add(new NotificadorSMS());

        // Configurar estrategias
        estrategias = new HashMap<>();
        estrategias.put(TipoMovimiento.RETIRO.name(), new RetiroStrategy());
        estrategias.put(TipoMovimiento.DEPOSITO.name(), new DepositoStrategy());
    }

    @Transactional
    public Movimiento realizarMovimiento(String numeroCuenta, BigDecimal monto, String tipoMovimiento) {
        Cuenta cuenta = cuentaPort.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new CuentaNoDisponibleException("Cuenta no encontrada"));

        OperacionStrategy estrategia = estrategias.get(tipoMovimiento);
        if (estrategia == null) {
            throw new TipoMovimientoInvalidoException("Tipo de movimiento no válido");
        }

        LocalDateTime timestamp = LocalDateTime.now();
        OperacionCommand comando = new OperacionCommand(cuenta, monto, estrategia, timestamp);
        String claveIdempotencia = comando.getClaveIdempotencia();

        if (movimientoPort.findByIdempotenciaClave(claveIdempotencia).isPresent()) {
            throw new OperacionDuplicadaException("Esta operación esta siendo procesada");
        }

        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(cuenta);
        movimiento.setValor(monto);
        movimiento.setTipoMovimiento(tipoMovimiento);
        movimiento.setFecha(timestamp.toLocalDate());
        movimiento.setIdempotenciaClave(claveIdempotencia);

        validadorCadena.validar(movimiento);

        try {
            comando.ejecutar();
            Movimiento movimientoGuardado = movimientoPort.crearMovimento(movimiento);
            cuentaPort.crearCuenta(cuenta);

            for (MovimientoObserver observador : observadores) {
                observador.notificar(movimientoGuardado);
            }

            return movimientoGuardado;
        } catch (Exception e) {
            comando.deshacer();
            throw e;
        }
    }
}

