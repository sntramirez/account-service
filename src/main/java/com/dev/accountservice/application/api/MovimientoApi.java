package com.dev.accountservice.application.api;


import com.dev.accountservice.application.services.MovimientoApplicationService;
import com.dev.accountservice.application.dto.MovimientoRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/movimientos")
public class MovimientoApi {

    @Autowired
    MovimientoApplicationService movimientoApplicationService;

    @PostMapping
    public ResponseEntity<?> crearMovimiento(@Valid @RequestBody MovimientoRequestDto movimiento) {
        return ResponseEntity.ok(movimientoApplicationService.crearMovimento(movimiento));

    }
}
