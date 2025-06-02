package com.mednova.alertas_service.controller;


import com.mednova.alertas_service.model.Retiro;
import com.mednova.alertas_service.service.AlertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alertas")
public class AlertaController {
    @Autowired
    private AlertaService alertaService;

    @PostMapping("/verificar-retiro")
    public ResponseEntity<String> verificarRetiro(@RequestBody Retiro retiro) {
        boolean alertaGenerada = alertaService.verificarYGenerarAlerta(retiro);
        if (alertaGenerada) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Alerta generada por retiro anticipado.");
        }
        return ResponseEntity.ok("Retiro permitido.");
    }
}
