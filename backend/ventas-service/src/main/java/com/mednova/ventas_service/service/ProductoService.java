package com.mednova.ventas_service.service;

import com.mednova.ventas_service.dto.ProductoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductoService {

    @Autowired
    private RestTemplate restTemplate;

    public ProductoDTO getProducto(int id_producto) {
        try {
            String url = "http://usuarios-service.usuarios.svc.cluster.local/api/inventarios/productos/" + id_producto;
            ResponseEntity<ProductoDTO> response = restTemplate.getForEntity(url, ProductoDTO.class);
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Error al obtener producto desde inventarios-service", e);
        }
    }

}

