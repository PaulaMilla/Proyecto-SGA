package com.mednova.fraccionamiento_service.service;

import com.mednova.fraccionamiento_service.dto.FraccionamientoRequest;
import com.mednova.fraccionamiento_service.model.Fraccionamiento;
import com.mednova.fraccionamiento_service.repository.FraccionamientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FraccionamientoService {

    private final RestTemplate restTemplate;
    private final FraccionamientoRepository fraccionamientoRepository;

    @Autowired
    public FraccionamientoService(RestTemplate restTemplate, FraccionamientoRepository fraccionamientoRepository) {
        this.restTemplate = restTemplate;
        this.fraccionamientoRepository = fraccionamientoRepository;
    }

    public ResponseEntity<String> realizarFraccionamiento(FraccionamientoRequest request) {
        // Llamada al microservicio de inventarios
        String url = "http://34.61.182.228/api/inventarios/fraccionar";


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<FraccionamientoRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        // Guardar fraccionamiento localmente
        Fraccionamiento fraccionamiento = new Fraccionamiento(
                request.getIdInventario(),
                request.getCantidadFraccionada(),
                request.getNuevoLote()
        );
        fraccionamientoRepository.save(fraccionamiento);

        return response;
    }
}
