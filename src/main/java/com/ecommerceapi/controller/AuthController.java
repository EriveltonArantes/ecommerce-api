package com.ecommerceapi.controller;

import com.ecommerceapi.model.UsuarioSistema;
import com.ecommerceapi.repository.UsuarioSistemaRepository;
import com.ecommerceapi.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Tag(name = "Autenticação", description = "Registro e login JWT")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioSistemaRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "Registrar novo usuário")
    @PostMapping("/registrar")
    public ResponseEntity<Map<String, String>> registrar(@RequestBody UsuarioSistema usuarioSistema) {
        if (usuarioSistema.getUsername() == null || usuarioSistema.getUsername().isBlank()
                || usuarioSistema.getPassword() == null || usuarioSistema.getPassword().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("erro", "username e password são obrigatórios"));
        }
        if (repository.findByUsername(usuarioSistema.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("erro", "Usuário já existe"));
        }
        usuarioSistema.setPassword(passwordEncoder.encode(usuarioSistema.getPassword()));
        if (usuarioSistema.getRole() == null || usuarioSistema.getRole().isBlank()) {
            usuarioSistema.setRole("USER");
        }
        repository.save(usuarioSistema);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Map.of("mensagem", "Usuário registrado com sucesso"));
    }

    @Operation(summary = "Login — retorna token JWT")
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UsuarioSistema login) {
        Optional<UsuarioSistema> existente = repository.findByUsername(login.getUsername());
        if (existente.isEmpty() || !passwordEncoder.matches(login.getPassword(), existente.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("erro", "Usuário ou senha inválidos"));
        }
        String role = existente.get().getRole();
        String resolvedRole = role == null ? "USER" : role;
        String token = jwtUtil.gerarToken(login.getUsername(), resolvedRole);
        return ResponseEntity.ok(Map.of(
            "token", token,
            "username", login.getUsername(),
            "role", resolvedRole
        ));
    }
}
