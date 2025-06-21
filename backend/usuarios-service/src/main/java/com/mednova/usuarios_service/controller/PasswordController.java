package com.mednova.usuarios_service.controller;

import com.mednova.usuarios_service.model.Usuario;
import com.mednova.usuarios_service.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PasswordController {
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostConstruct
    public void actualizarPasswords() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        for(Usuario usuario : usuarios) {
            if(!usuario.getPassword().startsWith("$2a$")){
                usuario.setPassword(encoder.encode(usuario.getPassword()));
                usuarioRepository.save(usuario);
            }
        }
    }
}
