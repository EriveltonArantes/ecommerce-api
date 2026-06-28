package com.ecommerceapi.controller;

import com.ecommerceapi.dto.CupomRequestDTO;
import com.ecommerceapi.dto.CupomResponseDTO;
import com.ecommerceapi.service.CupomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cupom", description = "Gerenciamento de cupoms")
@RestController
@RequestMapping("/api/cupoms")
public class CupomController {

    @Autowired
    private CupomService service;

    @Operation(summary = "Listar todos os Cupom")
    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<CupomResponseDTO>> listar(@RequestParam(required = false) String codigo, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.listar(codigo, page, size));
    }

    @Operation(summary = "Buscar Cupom por ID")
    @GetMapping("/{id}")
    public CupomResponseDTO buscar(@PathVariable Long id) { return service.buscar(id); }

    @Operation(summary = "Criar Cupom")
    @PostMapping
    public ResponseEntity<CupomResponseDTO> criar(@Valid @RequestBody CupomRequestDTO cupom) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(cupom));
    }

    @Operation(summary = "Atualizar Cupom")
    @PutMapping("/{id}")
    public CupomResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody CupomRequestDTO cupom) {
        return service.atualizar(id, cupom);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Excluir Cupom")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
