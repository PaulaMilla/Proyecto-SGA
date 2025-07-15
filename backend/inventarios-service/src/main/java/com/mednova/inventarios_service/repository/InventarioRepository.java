package com.mednova.inventarios_service.repository;

import com.mednova.inventarios_service.model.Farmacia;
import com.mednova.inventarios_service.model.Inventario;
import com.mednova.inventarios_service.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
    Optional<Inventario> findByProductoAndLoteAndFarmacia(Producto producto, String lote, Farmacia farmacia);

    @Modifying
    @Transactional
    @Query("UPDATE Inventario i SET i.cantidad_disponible = 0")
    void resetearCantidades();

    List<Inventario> findByFarmacia(Farmacia farmacia);
}
