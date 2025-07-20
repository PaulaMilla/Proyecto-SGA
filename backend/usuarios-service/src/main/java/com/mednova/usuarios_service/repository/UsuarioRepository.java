package com.mednova.usuarios_service.repository;

import java.util.List;
import java.util.Optional;

import com.mednova.usuarios_service.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mednova.usuarios_service.model.Usuario;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreo(String correo);
    @Query("SELECT DISTINCT u.nombre_farmacia FROM Usuario u WHERE u.nombre_farmacia IS NOT NULL")
    List<String> obtenerNombresDeFarmacias();


}
