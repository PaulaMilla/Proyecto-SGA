package com.mednova.ventas_service.service;

import com.mednova.ventas_service.dto.ProductoDTO;
import com.mednova.ventas_service.model.DetalleVenta;
import com.mednova.ventas_service.model.Venta;
import com.mednova.ventas_service.repository.DetalleVentaRepository;
import com.mednova.ventas_service.repository.VentaRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {
    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;

    public VentaService(VentaRepository ventaRepository, DetalleVentaRepository detalleVentaRepository) {
        this.ventaRepository = ventaRepository;
        this.detalleVentaRepository = detalleVentaRepository;
    }

    @Autowired
    private ProductoService productoService;

    @Autowired
    private RestTemplate restTemplate;

    public Venta registrarVenta(Venta venta, List<DetalleVenta> detalles) {
        double totalVenta = 0.0;

        for (DetalleVenta detalle : detalles) {
            // Obtener datos del producto
            ProductoDTO producto = productoService.obtenerProductoPorId(detalle.getProductoId());

            // Calcular subtotal
            double subtotal = detalle.getCantidad() * producto.getPrecio_unitario();

            // Rellenar precios
            detalle.setPrecioUnitario(producto.getPrecio_unitario());
            detalle.setSubtotal(subtotal);

            totalVenta += subtotal;
        }

        venta.setTotal(totalVenta);
        venta.setFechaVenta(LocalDate.now());

        // Guardar venta
        Venta ventaGuardada = ventaRepository.save(venta);

        // Guardar detalle con ventaId
        for (DetalleVenta detalle : detalles) {
            detalle.setVentaId(ventaGuardada.getId());
            detalleVentaRepository.save(detalle);
        }

        return ventaGuardada;
    }

  /**  public Venta registrarVenta(Venta venta){
        Venta ventaGuardada = ventaRepository.save(venta);
        return ventaGuardada;
    }


    public Venta registrarVenta(Venta venta, List<DetalleVenta> detalles) {
        // 1. Guardar venta
        Venta ventaGuardada = ventaRepository.save(venta);

        // 2. Procesar detalles de venta
        for (DetalleVenta detalle : detalles) {
            detalle.setVentaId(ventaGuardada.getId());
            detalleVentaRepository.save(detalle);

            // 3. Descontar stock llamando a inventarios-service
            descontarStock(detalle.getProductoId(), detalle.getCantidad());
        }

        return ventaGuardada;
    }**/

    public List<Venta> listarTodas() {
        return ventaRepository.findAll();
    }

    public Optional<Venta> obtenerPorId(Integer id) {
        return ventaRepository.findById(id);
    }

    public List<Venta> obtenerPorPaciente(Integer pacienteId) {
        return ventaRepository.findByPacienteId(pacienteId);
    }

    public List<Venta> obtenerPorRangoFechas(LocalDate desde, LocalDate hasta) {
        return ventaRepository.findByFechaVentaBetween(desde, hasta);
    }

    public DetalleVenta obtenerDetallePorVenta(Integer ventaId) {
        return detalleVentaRepository.findByVentaId(ventaId);
    }

    private void descontarStock(int productoId, int cantidad) {
        String url = "http://inventarios-service/api/inventario/descontar"; // Ajusta según tu ruta real

        // Crear un DTO simple para enviar al otro microservicio
        var request = new DescuentoRequest(productoId, cantidad);
        restTemplate.postForEntity(url, request, Void.class);
    }

    public static class DescuentoRequest {
        private int productoId;
        @Setter
        private int cantidad;

        public DescuentoRequest(int productoId, int cantidad) {
            this.productoId = productoId;
            this.cantidad = cantidad;
        }

        public int getProductoId() { return productoId; }
        public void setProductoId(Integer productoId) { this.productoId = productoId; }
        public int getCantidad() { return cantidad; }
    }
}
