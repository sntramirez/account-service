package com.dev.accountservice.application.api;

import com.dev.accountservice.domain.core.CuentaServicio;
import com.dev.accountservice.domain.core.model.CuentaDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
