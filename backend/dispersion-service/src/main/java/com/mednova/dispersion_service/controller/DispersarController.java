package com.mednova.dispersion_service.controller;

import com.mednova.dispersion_service.dto.DispersarRequest;
import com.mednova.dispersion_service.service.DispersarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dispersion")
public class DispersarController {

    @Autowired
    private DispersarService dispersarService;

    @PostMapping
    public ResponseEntity<String> dispersar(@RequestBody DispersarRequest request) {
        String mensaje = dispersarService.dispersarMedicamento(request);
        return ResponseEntity.ok(mensaje);
    }
}
