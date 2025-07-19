package com.mednova.ventas_service.service;

import com.mednova.ventas_service.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class UsuarioService {

    @Autowired
    private RestTemplate restTemplate;

    public UsuarioDTO getUsuario(int id) {
        try {
            String url = "http://usuarios-service.usuarios.svc.cluster.local/api/usuarios/" + id;
            ResponseEntity<UsuarioDTO> response = restTemplate.getForEntity(url, UsuarioDTO.class);
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Error al obtener usuario desde usuarios-service", e);
        }
    }

}