package com.mednova.usuarios_service.repository;

import java.util.List;
import java.util.Optional;

import com.mednova.usuarios_service.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mednova.usuarios_service.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreo(String correo);
    
}
