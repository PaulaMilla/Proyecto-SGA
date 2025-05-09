package com.mednova.ventas_service.controller;

import com.mednova.ventas_service.model.Venta;
import com.mednova.ventas_service.service.VentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ventas")
public class VentasController {

    private final VentaService ventaService;
    public VentasController(VentaService ventaService) { this.ventaService = ventaService; }


}
