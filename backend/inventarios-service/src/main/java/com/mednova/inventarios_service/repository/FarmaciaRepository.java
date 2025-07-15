package com.mednova.inventarios_service.repository;

import com.mednova.inventarios_service.model.Farmacia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FarmaciaRepository extends JpaRepository<Farmacia, Integer> {
    Optional<Farmacia> findByNombre(String nombre);

    @Modifying
    @Query("DELETE FROM Farmacia i")
    void deleteAllFarmacias();
}
