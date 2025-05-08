package com.mednova.usuarios_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mednova.usuarios_service.model.Permiso;

public interface PermisoRepository extends JpaRepository<Permiso, Integer> {
    
}
