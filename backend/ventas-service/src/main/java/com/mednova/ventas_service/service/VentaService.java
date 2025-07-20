package com.mednova.ventas_service.service;

import com.mednova.ventas_service.dto.DescuentoStockRequest;
import com.mednova.ventas_service.dto.DetalleVentaDTO;
import com.mednova.ventas_service.dto.ProductoDTO;
import com.mednova.ventas_service.dto.VentaConDetallesDTO;
import com.mednova.ventas_service.model.DetalleVenta;
import com.mednova.ventas_service.model.Venta;
import com.mednova.ventas_service.repository.DetalleVentaRepository;
import com.mednova.ventas_service.repository.VentaRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public List<DetalleVenta> obtenerDetallesPorVentaId(int ventaId) {
        return detalleVentaRepository.findByVentaId(ventaId);
    }

    public Venta registrarVentaConDetalles(VentaConDetallesDTO dto) {
        Venta venta = new Venta();
        venta.setPacienteId(dto.getPacienteId());
        venta.setIdUsuarioVendedor(dto.getUsuarioId());
        venta.setFechaVenta(LocalDate.now());
        venta.setPagada(false);

        Venta ventaGuardada = ventaRepository.save(venta);

        double totalVenta = 0.0;

        for (DetalleVentaDTO d : dto.getDetalles()) {
            ProductoDTO producto = productoService.getProducto(d.getProductoId());

            double subtotal = d.getCantidad() * producto.getPrecio_unitario();

            // Crear y guardar detalle
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVentaId(ventaGuardada.getId());
            detalle.setProductoId(d.getProductoId());
            detalle.setCantidad(d.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio_unitario());
            detalle.setSubtotal(subtotal);

            detalleVentaRepository.save(detalle);

            totalVenta += subtotal;

            // ⬇️ DESCONTAR STOCK EN INVENTARIOS ⬇️
            DescuentoStockRequest descuentoRequest = new DescuentoStockRequest();
            descuentoRequest.setProductoId(d.getProductoId());
            descuentoRequest.setCantidad(d.getCantidad());

            try {
                String url = "http://inventarios-service.inventarios.svc.cluster.local/api/inventarios/descontar-stock";
                restTemplate.postForEntity(url, descuentoRequest, Void.class);
            } catch (Exception e) {
                throw new RuntimeException("Error al descontar stock para producto " + d.getProductoId(), e);
            }
            // ⬆️ FIN DESCUENTO STOCK ⬆️
        }

        ventaGuardada.setTotal(totalVenta);
        ventaRepository.save(ventaGuardada);

        return ventaGuardada;
    }

    public void marcarVentaComoPagada(int idVenta){
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new RuntimeException("No existe una venta con id: " + idVenta));

        venta.setPagada(true);
        ventaRepository.save(venta);
    }

    public List<Venta> obtenerNoPagadas() {
        return ventaRepository.findByPagadaFalse();
    }


   /* public Venta registrarVenta(Venta venta, List<DetalleVenta> detalles) {
        double totalVenta = 0.0;

        for (DetalleVenta detalle : detalles) {
            // Obtener datos del producto
            ProductoDTO producto = productoService.getProducto(detalle.getProductoId());

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
    }*/

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


    private void descontarStock(int productoId, int cantidad) {
        String url = "http://inventarios-service/api/inventario/descontar"; // Ajusta según tu ruta real

        // Crear un DTO simple para enviar al otro microservicio
        var request = new DescuentoRequest(productoId, cantidad);
        restTemplate.postForEntity(url, request, Void.class);
    }

    @Transactional
    public void eliminarVentaConDetalles(int ventaId) {
        // Primero eliminamos los detalles asociados
        detalleVentaRepository.deleteByVentaId(ventaId);
        // Luego eliminamos la venta
        ventaRepository.deleteById(ventaId);
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
