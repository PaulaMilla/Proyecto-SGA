package com.mednova.usuarios_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mednova.usuarios_service.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByCorreo(String correo);
    List<Usuario> findAllByRol(String rol_usuario);
    
}
