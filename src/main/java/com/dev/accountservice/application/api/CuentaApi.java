package com.dev.accountservice.application.api;

import com.dev.accountservice.domain.core.model.CuentaDto;
import com.dev.accountservice.domain.core.CuentaServicio;
import com.dev.accountservice.domain.core.model.RespuestaMovimiento;
import com.dev.accountservice.domain.core.model.SaldoNoDisponibleException;
import com.dev.accountservice.domain.core.model.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/cuentas")
public class CuentaApi {

    @Autowired
    CuentaServicio cuentaServicio;

    @PostMapping
    public ResponseEntity<?> crearCuenta(@Valid @RequestBody CuentaDto cuentaDto) {
        return ResponseEntity.ok(cuentaServicio.crearCuenta(cuentaDto));
    }
}
