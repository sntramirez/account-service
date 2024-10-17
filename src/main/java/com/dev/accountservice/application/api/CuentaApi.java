package com.dev.accountservice.application.api;


import com.dev.accountservice.application.services.CuentaApplicationService;
import com.dev.accountservice.application.dto.CuentaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/cuentas")
public class CuentaApi {

    @Autowired
    CuentaApplicationService cuentaApplicationService;

    @PostMapping
    public ResponseEntity<?> crearCuenta(@Valid @RequestBody CuentaDto cuentaDto) {
        return ResponseEntity.ok(cuentaApplicationService.crearCuenta(cuentaDto));
    }
}
