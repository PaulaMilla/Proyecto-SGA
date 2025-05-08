package com.mednova.usuarios_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mednova.usuarios_service.model.Permiso;
import com.mednova.usuarios_service.model.Rol;
import com.mednova.usuarios_service.model.Usuario;
import com.mednova.usuarios_service.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
  
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Rol>> listarRoles() {
        return ResponseEntity.ok(usuarioService.listarRoles());
    }

    @GetMapping("/permisos")
    public ResponseEntity<List<Permiso>> listarPermisos() {
        return ResponseEntity.ok(usuarioService.listarPermisos());
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.crearUsuario(usuario));
    }

    @PostMapping("/roles")
    public ResponseEntity<Rol> crearRol(@RequestBody Rol rol) {
        return ResponseEntity.ok(usuarioService.crearRol(rol));
    }

    @PostMapping("/permisos")
    public ResponseEntity<Permiso> crearPermiso(@RequestBody Permiso permiso) {
        return ResponseEntity.ok(usuarioService.crearPermiso(permiso));
    }
    
}
