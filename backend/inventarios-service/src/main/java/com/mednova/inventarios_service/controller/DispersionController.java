package com.mednova.inventarios_service.controller;

import com.mednova.inventarios_service.dto.DispersarRequest;
import com.mednova.inventarios_service.service.InventarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dispersion")
@RequiredArgsConstructor
public class DispersionController {

    private final InventarioService inventarioService;

    @PostMapping("/dispersar")
    public ResponseEntity<String> dispersarMedicamento(@RequestBody DispersarRequest request) {
        try {
            inventarioService.dispersarMedicamento(request);
            return ResponseEntity.ok("Medicamento dispersado correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
