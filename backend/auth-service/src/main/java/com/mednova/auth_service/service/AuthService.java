package com.mednova.auth_service.service;

import com.mednova.auth_service.security.JwtUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
@Service
public class AuthService {
    private final WebClient webClient;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private String rolActual;
    private List<String> permisosActuales;

    public AuthService(WebClient.Builder builder, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.webClient = builder.baseUrl("http://usuarios-service.usuarios.svc.cluster.local").build();
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    public String getRolActual() {
        return rolActual;
    }
    public List<String> getPermisos() {
        return permisosActuales;
    }

    public String login(String email, String password){
        Map<String, Object> usuario = webClient.get()
                .uri("api/usuarios/email/"+email)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (usuario == null || !passwordEncoder.matches(password, (String) usuario.get("password"))) {
            throw new RuntimeException("Credenciales inválidas");
        }

        rolActual = (String) usuario.get("rol");
        permisosActuales = (List<String>) usuario.get("permisos");

        return jwtUtils.generateToken((String) usuario.get("email"), rolActual);
    }


}
