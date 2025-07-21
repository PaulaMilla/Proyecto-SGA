package com.mednova.dispersion_service.controller;

import com.mednova.dispersion_service.dto.DispersionRequest;
import com.mednova.dispersion_service.service.DispersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dispersion")
public class DispersionController {

    private final DispersionService dispersionService;

    @Autowired
    public DispersionController(DispersionService dispersionService) {
        this.dispersionService = dispersionService;
    }

    @PostMapping("/dispersar")
    public ResponseEntity<String> dispersar(@RequestBody DispersionRequest request) {
        return dispersionService.dispersar(request);
    }
}
