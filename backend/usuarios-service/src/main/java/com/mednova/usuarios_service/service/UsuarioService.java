package com.mednova.usuarios_service.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mednova.usuarios_service.model.Permiso;
import com.mednova.usuarios_service.model.Rol;
import com.mednova.usuarios_service.model.Usuario;
import com.mednova.usuarios_service.repository.PermisoRepository;
import com.mednova.usuarios_service.repository.RolRepository;
import com.mednova.usuarios_service.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PermisoRepository permisoRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository, PermisoRepository permisoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.permisoRepository = permisoRepository;
    }

    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }
    public Usuario actualizarUsuario(Integer id, Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);
        if (usuarioExistente != null) {
            usuarioExistente.setNombre_usuario(usuario.getNombre_usuario());
            usuarioExistente.setCorreo_usuario(usuario.getCorreo_usuario());
            usuarioExistente.setRol_usuario(usuario.getRol_usuario());
            return usuarioRepository.save(usuarioExistente);
        }
        return null;
    }

    public void eliminarUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }

    public Rol crearRol(Rol rol) {
        return rolRepository.save(rol);
    }

    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }

    public Permiso crearPermiso(Permiso permiso) {
        return permisoRepository.save(permiso);
    }

    public List<Permiso> listarPermisos() {
        return permisoRepository.findAll();
    }

}
