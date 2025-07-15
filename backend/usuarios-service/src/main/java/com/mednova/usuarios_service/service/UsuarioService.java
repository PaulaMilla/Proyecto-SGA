package com.mednova.usuarios_service.service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.mednova.usuarios_service.dto.RolRequestDTO;
import com.mednova.usuarios_service.dto.UsuarioRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository, PermisoRepository permisoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.permisoRepository = permisoRepository;
    }

    public Usuario crearUsuario(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(encoder.encode(dto.getPassword()));
        usuario.setEstado(dto.getEstado());
        usuario.setNombre_farmacia(dto.getNombre_farmacia());
        usuario.setFechaCreacion(new Date());

        Rol rol = rolRepository.findById(dto.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + dto.getIdRol()));
        usuario.setRol(rol);

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario actualizarUsuario(Integer id, Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);
        if (usuarioExistente != null) {
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setCorreo(usuario.getCorreo());
            usuarioExistente.setRol(usuario.getRol());
            return usuarioRepository.save(usuarioExistente);
        }
        return null;
    }

    public void eliminarUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }

    public Rol crearRol(RolRequestDTO dto) {
        Rol rol = new Rol();
        rol.setNombre_rol(dto.getNombre_rol());
        rol.setDescripcion_rol(dto.getDescripcion_rol());

        // Buscar los permisos por ID
        List<Permiso> permisos = permisoRepository.findAllById(dto.getPermisos());
        rol.setPermisos(permisos);

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

    public Optional<Usuario> obtenerPorEmail(String email){
        return usuarioRepository.findByCorreo(email);
    }

}
