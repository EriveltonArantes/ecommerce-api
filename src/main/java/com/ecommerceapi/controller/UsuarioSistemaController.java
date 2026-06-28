package com.ecommerceapi.controller;

import com.ecommerceapi.dto.UsuarioSistemaRequestDTO;
import com.ecommerceapi.dto.UsuarioSistemaResponseDTO;
import com.ecommerceapi.service.UsuarioSistemaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "UsuarioSistema", description = "Gerenciamento de usuariosistemas")
@RestController
@RequestMapping("/api/usuariosistemas")
public class UsuarioSistemaController {

    @Autowired
    private UsuarioSistemaService service;

    @Operation(summary = "Listar todos os UsuarioSistema")
    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<UsuarioSistemaResponseDTO>> listar(@RequestParam(required = false) String username, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.listar(username, page, size));
    }

    @Operation(summary = "Buscar UsuarioSistema por ID")
    @GetMapping("/{id}")
    public UsuarioSistemaResponseDTO buscar(@PathVariable Long id) { return service.buscar(id); }

    @Operation(summary = "Criar UsuarioSistema")
    @PostMapping
    public ResponseEntity<UsuarioSistemaResponseDTO> criar(@Valid @RequestBody UsuarioSistemaRequestDTO usuarioSistema) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(usuarioSistema));
    }

    @Operation(summary = "Atualizar UsuarioSistema")
    @PutMapping("/{id}")
    public UsuarioSistemaResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioSistemaRequestDTO usuarioSistema) {
        return service.atualizar(id, usuarioSistema);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Excluir UsuarioSistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
