package com.mednova.ventas_service.service;

import com.mednova.ventas_service.dto.ProductoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductoService {

    @Autowired
    private RestTemplate restTemplate;

    public ProductoDTO obtenerProductoPorId(int productoId) {
        String url = "http://inventarios-service:80" + productoId;
        ResponseEntity<ProductoDTO> response = restTemplate.getForEntity(url, ProductoDTO.class);
        return response.getBody();
    }
}

