package com.mednova.ventas_service.controller;

import com.mednova.ventas_service.model.DetalleVenta;
import com.mednova.ventas_service.service.VentaService;
import com.mednova.ventas_service.model.Venta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ventas")
public class VentasController {

    private final VentaService ventaService;

    public VentasController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    // POST /ventas - Registrar nueva venta
    @PostMapping
    public ResponseEntity<Venta> registrarVenta(@RequestBody Venta venta) {
        Venta nuevaVenta = ventaService.registrarVenta(venta);
        return ResponseEntity.ok(nuevaVenta);
    }

    // GET /ventas - Listar todas las ventas
    @GetMapping
    public ResponseEntity<List<Venta>> listarVentas() {
        return ResponseEntity.ok(ventaService.listarTodas());
    }

    // GET /ventas/{id} - Detalle de una venta
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Venta>> obtenerVentaPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ventaService.obtenerPorId(id));
    }

    // GET /ventas/paciente/{pacienteId} - Ventas por paciente
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Venta>> obtenerPorPaciente(@PathVariable Integer pacienteId) {
        return ResponseEntity.ok(ventaService.obtenerPorPaciente(pacienteId));
    }

    // GET /ventas/fecha?desde=...&hasta=... - Ventas por rango de fechas
    @GetMapping("/fecha")
    public ResponseEntity<List<Venta>> obtenerPorRangoFechas(
            @RequestParam("desde") LocalDate desde,
            @RequestParam("hasta") LocalDate hasta) {
        return ResponseEntity.ok(ventaService.obtenerPorRangoFechas(desde, hasta));
    }
}

