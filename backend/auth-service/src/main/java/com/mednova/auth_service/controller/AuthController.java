package com.mednova.auth_service.controller;

import com.mednova.auth_service.dto.JwtResponse;
import com.mednova.auth_service.dto.LoginRequest;
import com.mednova.auth_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        String jwt = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(
                new JwtResponse(jwt, request.getEmail(), authService.getRolActual())
        );
    }

}
