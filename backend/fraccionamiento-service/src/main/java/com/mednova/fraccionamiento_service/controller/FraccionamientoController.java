package com.mednova.fraccionamiento_service.controller;

import com.mednova.fraccionamiento_service.dto.FraccionamientoRequest;
import com.mednova.fraccionamiento_service.service.FraccionamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fraccionamiento")
public class FraccionamientoController {

    private final FraccionamientoService fraccionamientoService;

    @Autowired
    public FraccionamientoController(FraccionamientoService fraccionamientoService) {
        this.fraccionamientoService = fraccionamientoService;
    }

    @PostMapping
    public ResponseEntity<String> fraccionar(@RequestBody FraccionamientoRequest request) {
        return fraccionamientoService.realizarFraccionamiento(request);
    }
}
