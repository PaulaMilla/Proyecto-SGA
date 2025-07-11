package com.mednova.auth_service.service;

import com.mednova.auth_service.security.JwtUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
@Service
public class AuthService {
    private final WebClient webClient;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private String rolActual;

    public AuthService(WebClient.Builder builder, JwtUtils jwtUtils) {
        this.webClient = builder.baseUrl("http://usuarios-service.usuarios.svc.cluster.local").build();
        this.jwtUtils = jwtUtils;
    }

    public String getRolActual() {
        return rolActual;
    }

    public String login(String email, String password){
        Map<String, Object> usuario = webClient.get()
                .uri("api/usuarios/email/"+email)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if(usuario == null || !encoder.matches(password,(String) usuario.get("password"))){
            throw new RuntimeException("Credenciales inválidas");
        }

        rolActual = (String) usuario.get("rol");
        //Generar JWT con claims
        return jwtUtils.generateToken((String) usuario.get("email"), rolActual);
    }


}
