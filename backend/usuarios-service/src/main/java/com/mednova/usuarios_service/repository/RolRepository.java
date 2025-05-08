package com.mednova.usuarios_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mednova.usuarios_service.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    
}
