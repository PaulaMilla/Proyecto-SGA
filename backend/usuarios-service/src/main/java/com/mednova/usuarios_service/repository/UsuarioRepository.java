package com.mednova.usuarios_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mednova.usuarios_service.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByCorreo_usuario(String correo_usuario);
    List<Usuario> findAllByRol_usuario(String rol_usuario);
    
}
