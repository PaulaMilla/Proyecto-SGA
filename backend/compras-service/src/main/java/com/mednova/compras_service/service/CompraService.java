package com.mednova.compras_service.service;

import com.mednova.compras_service.model.Compra;
import com.mednova.compras_service.model.DetalleCompra;
import com.mednova.compras_service.model.EstadoCompra;
import com.mednova.compras_service.repository.CompraRepository;
import com.mednova.compras_service.repository.ProveedorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CompraService {
    private final CompraRepository compraRepository;
    private final ProveedorRepository proveedorRepository;

    public CompraService(CompraRepository compraRepository, ProveedorRepository proveedorRepository) {
        this.compraRepository = compraRepository;
        this.proveedorRepository = proveedorRepository;
    }

    public Compra crearCompra(Compra compra) {
        // Calcular total y subtotales
        BigDecimal total = BigDecimal.ZERO;

        for (DetalleCompra detalle : compra.getDetalles()) {
            BigDecimal subtotal = detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(detalle.getCantidad()));
            detalle.setSubtotal(subtotal);
            detalle.setCompra(compra); // Setear relación inversa
            total = total.add(subtotal);
        }

        compra.setTotal(total);
        compra.setEstado(EstadoCompra.REGISTRADA);
        return compraRepository.save(compra);
    }

    public List<Compra> listarCompras() {
        return compraRepository.findAll();
    }

    public Optional<Compra> obtenerPorId(int id) {
        return compraRepository.findById(id);
    }

    public List<Compra> buscarPorProveedor(String nombre) {
        return compraRepository.findByProveedor_NombreContainingIgnoreCase(nombre);
    }

    public Compra anularCompra(int id) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Compra no encontrada"));

        compra.setEstado(EstadoCompra.ANULADA);
        return compraRepository.save(compra);
    }
}
