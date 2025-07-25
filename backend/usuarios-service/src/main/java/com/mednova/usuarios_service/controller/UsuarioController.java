package com.mednova.usuarios_service.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mednova.usuarios_service.dto.*;
import com.mednova.usuarios_service.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.mednova.usuarios_service.model.Permiso;
import com.mednova.usuarios_service.model.Rol;
import com.mednova.usuarios_service.model.Usuario;
import com.mednova.usuarios_service.service.UsuarioService;

//Controller para manejar las peticiones relacionadas con los usuarios, roles y permisos
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        List<UsuarioResponseDTO> dtos = usuarios.stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/response")
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerUsuarios() {
        List<UsuarioResponseDTO> usuarios = usuarioService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Usuario>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    @GetMapping("/roles")
    public List<RolDTO> listarRoles() {
        return usuarioService.obtenerTodosLosRoles();
    }

    @GetMapping("/farmacias")
    public ResponseEntity<List<String>> obtenerNombresDeFarmacias() {
        return ResponseEntity.ok(usuarioService.obtenerNombresDeFarmacias());
    }

    @GetMapping("/permisos")
    public ResponseEntity<List<Permiso>> listarPermisos() {
        return ResponseEntity.ok(usuarioService.listarPermisos());
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody UsuarioRequestDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.crearUsuario(usuarioDTO));
    }

    @PutMapping("/usuarios")
    public ResponseEntity<Usuario> actualizarUsuario(@RequestBody UsuarioUpdateDTO dto) {
        Usuario actualizado = usuarioService.actualizarUsuario(dto);
        return ResponseEntity.ok(actualizado);
    }

    @PostMapping("/roles")
    public ResponseEntity<Rol> crearRol(@RequestBody RolRequestDTO rolDTO) {
        return ResponseEntity.ok(usuarioService.crearRol(rolDTO));
    }

    @PostMapping("/permisos")
    public ResponseEntity<Permiso> crearPermiso(@RequestBody Permiso permiso) {
        return ResponseEntity.ok(usuarioService.crearPermiso(permiso));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> obtenerUsuarioPorEmail(@PathVariable String email) {
        Optional<Usuario> usuarioOpt = usuarioService.obtenerPorEmail(email);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        Usuario usuario = usuarioOpt.get();
        String rol = usuario.getRol().getNombre_rol();
        List<String> permisos = usuario.getRol().getPermisos().stream()
                .map(Permiso::getNombre_permiso)
                .collect(Collectors.toList());

        UsuarioLoginDTO dto = new UsuarioLoginDTO(
                usuario.getCorreo(),
                rol,
                permisos,
                usuario.getPassword()
        );

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/usuario-email/{email}")
    public ResponseEntity<?> obtenerUsuarioPorCorreo(@PathVariable String email) {
        Optional<Usuario> usuarioOpt = usuarioService.obtenerPorEmail(email);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        Usuario usuario = usuarioOpt.get();
        UsuarioResponseDTO dto = new UsuarioResponseDTO(usuario);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/nombre-farmacia/{emailUsuario}")
    public ResponseEntity<String> obtenerNombreFarmaciaPorEmail(@PathVariable String emailUsuario) {
        Optional<Usuario> usuarioOpt = usuarioService.obtenerPorEmail(emailUsuario);

        if (usuarioOpt.isPresent()) {
            String nombreFarmacia = usuarioOpt.get().getNombre_farmacia();

            if (nombreFarmacia == null || nombreFarmacia.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("El usuario no tiene una farmacia asignada.");
            }

            return ResponseEntity.ok(nombreFarmacia);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado.");
        }
    }

    @PostMapping("/hash-passwords")
    public ResponseEntity<String> actualizarPasswords() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        for (Usuario usuario : usuarios) {
            if (!usuario.getPassword().startsWith("$2a$")) {
                usuario.setPassword(encoder.encode(usuario.getPassword()));
                usuarioRepository.save(usuario);
            }
        }
        return ResponseEntity.ok("Contraseñas actualizadas");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
