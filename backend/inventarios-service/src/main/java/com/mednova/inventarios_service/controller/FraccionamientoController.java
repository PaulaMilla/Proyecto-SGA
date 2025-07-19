package com.mednova.inventarios_service.controller;

import com.mednova.inventarios_service.dto.FraccionamientoRequest;
import com.mednova.inventarios_service.service.InventarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fraccionamiento")
public class FraccionamientoController {

    private final InventarioService inventarioService;

    public FraccionamientoController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @PostMapping
    public ResponseEntity<String> fraccionar(@RequestBody FraccionamientoRequest request) {
        try {
            inventarioService.realizarFraccionamiento(request);
            return ResponseEntity.ok("Fraccionamiento realizado con éxito.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno: " + e.getMessage());
        }
    }
}
