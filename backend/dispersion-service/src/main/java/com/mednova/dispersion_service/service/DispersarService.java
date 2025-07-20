package com.mednova.dispersion_service.service;

import com.mednova.dispersion_service.dto.DescuentoRequest;
import com.mednova.dispersion_service.dto.DispersarRequest;
import com.mednova.dispersion_service.model.Dispersion;
import com.mednova.dispersion_service.repository.DispersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;

@Service
public class DispersarService {

    @Autowired
    private DispersionRepository dispersionRepository;

    private final WebClient webClient = WebClient.create("http://34.61.182.228:8080");


    public String dispersarMedicamento(DispersarRequest request) {
        // Llamar al microservicio de inventario
        webClient.post()
                .uri("/api/inventario/descontar")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block(); // bloquear para que termine la llamada

        // Guardar dispensación
        Dispersion dispensacion = new Dispersion();
        dispensacion.setIdInventario(request.getIdInventario());
        dispensacion.setCantidad(request.getCantidad());
        dispensacion.setIdPaciente(request.getIdPaciente());
        dispensacion.setFecha(LocalDate.now().toString());

        dispersionRepository.save(dispensacion);

        return "Dispensación registrada con éxito.";
    }
}
