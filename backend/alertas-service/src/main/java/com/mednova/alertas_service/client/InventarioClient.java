package com.mednova.alertas_service.client;

import com.mednova.alertas_service.client.dto.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventario-client", url = "${feign.client.config.inventario-client.url}")
public interface InventarioClient {

    @GetMapping("/api/inventarios/productos/{id_producto}")
    ProductoDTO obtenerProductoPorId(@PathVariable("id_producto") int id_producto);
}