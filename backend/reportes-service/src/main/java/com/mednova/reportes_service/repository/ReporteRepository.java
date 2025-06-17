package com.mednova.reportes_service.repository;

import com.mednova.reportes_service.model.ReporteVenta;
import com.mednova.reportes_service.model.VentaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public interface ReporteRepository extends JpaRepository<ReporteVenta, Integer> {

/*    @Autowired
    private RestTemplate restTemplate;

    public List<VentaDTO> obtenerReportePorFechas(LocalDate desde, LocalDate hasta) {
        // URL construida con las fechas como parámetros
        String url = "http://api-gateway/api/ventas/fecha?desde=" + desde + "&hasta=" + hasta;

        // hacemos la petición GET a la URL, esperando un array de VentaDTO como respuesta
        VentaDTO[] ventas = restTemplate.getForObject(url, VentaDTO[].class);

        return Arrays.asList(ventas);
    }
*/
}
