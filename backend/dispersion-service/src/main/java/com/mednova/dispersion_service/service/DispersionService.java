package com.mednova.dispersion_service.service;

import com.mednova.dispersion_service.dto.DispersionRequest;
import com.mednova.dispersion_service.model.Dispersion;
import com.mednova.dispersion_service.repository.DispersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DispersionService {

    private final DispersionRepository dispersionRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public DispersionService(DispersionRepository dispersionRepository, RestTemplate restTemplate) {
        this.dispersionRepository = dispersionRepository;
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> dispersar(DispersionRequest request) {
        String url = "http://inventarios-service/api/inventarios/descontar";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<DispersionRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Dispersion dispersion = new Dispersion(
                    request.getIdInventario(),
                    request.getCantidad(),
                    request.getEmailUsuario()
            );
            dispersionRepository.save(dispersion);
        }

        return response;
    }

}

