package com.dev.accountservice.application.api;


import com.dev.accountservice.application.services.MovimientoApplicationService;
import com.dev.accountservice.domain.core.model.MovimientoDto;
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
@RequestMapping("/movimientos")
public class MovimientoApi {

    @Autowired
    MovimientoApplicationService movimientoApplicationService;

    @PostMapping
    public ResponseEntity<?> crearMovimiento(@Valid @RequestBody MovimientoDto movimiento) {
        return ResponseEntity.ok(movimientoApplicationService.crearMovimento(movimiento));

    }
}
