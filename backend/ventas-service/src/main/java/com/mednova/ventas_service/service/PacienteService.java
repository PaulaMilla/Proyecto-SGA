package com.mednova.ventas_service.service;

import com.mednova.ventas_service.dto.PacienteDTO;
import com.mednova.ventas_service.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class PacienteService {

    @Autowired
    private RestTemplate restTemplate;

    public PacienteDTO getPaciente(int id_paciente) {
        try {
            String url = "http://pacientes-service.pacientes.svc.cluster.local/api/pacientes/" + id_paciente;
            ResponseEntity<PacienteDTO> response = restTemplate.getForEntity(url, PacienteDTO.class);
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Error al obtener paciente desde pacientes-service", e);
        }
    }

}
