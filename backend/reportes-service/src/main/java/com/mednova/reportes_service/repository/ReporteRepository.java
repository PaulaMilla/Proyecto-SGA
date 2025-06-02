package com.mednova.reportes_service.repository;

import com.mednova.reportes_service.model.VentaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Repository
public class ReporteRepository {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Consulta las ventas en un rango de fechas llamando a la API vía Gateway.
     * @param desde Fecha inicial del rango.
     * @param hasta Fecha final del rango.
     * @return Lista de objetos VentaDTO obtenidos desde ventas-service.
     */
    public List<VentaDTO> obtenerReportePorFechas(LocalDate desde, LocalDate hasta) {
        // URL construida con las fechas como parámetros
        String url = "http://api-gateway/api/ventas/fecha?desde=" + desde + "&hasta=" + hasta;

        // hacemos la petición GET a la URL, esperando un array de VentaDTO como respuesta
        VentaDTO[] ventas = restTemplate.getForObject(url, VentaDTO[].class);

        return Arrays.asList(ventas);
    }

}
