package com.mednova.inventarios_service.repository;

import com.mednova.inventarios_service.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
    @Query("SELECT i FROM Inventario i WHERE i.id_producto = :idProducto AND i.lote = :lote AND i.nombre_farmacia = :nombreFarmacia")
    Optional<Inventario> findByProductoYLoteYFarmacia(
            @Param("idProducto") int idProducto,
            @Param("lote") String lote,
            @Param("nombreFarmacia") String nombreFarmacia
    );

    @Modifying
    @Transactional
    @Query("UPDATE Inventario i SET i.cantidad_disponible = 0")
    void resetearCantidades();
}
