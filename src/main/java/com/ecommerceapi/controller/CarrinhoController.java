package com.ecommerceapi.controller;

import com.ecommerceapi.dto.CarrinhoRequestDTO;
import com.ecommerceapi.dto.CarrinhoResponseDTO;
import com.ecommerceapi.service.CarrinhoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Carrinho", description = "Gerenciamento de carrinhos")
@RestController
@RequestMapping("/api/carrinhos")
public class CarrinhoController {

    @Autowired
    private CarrinhoService service;

    @Operation(summary = "Listar todos os Carrinho")
    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<CarrinhoResponseDTO>> listar(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.listar(page, size));
    }

    @Operation(summary = "Buscar Carrinho por ID")
    @GetMapping("/{id}")
    public CarrinhoResponseDTO buscar(@PathVariable Long id) { return service.buscar(id); }

    @Operation(summary = "Criar Carrinho")
    @PostMapping
    public ResponseEntity<CarrinhoResponseDTO> criar(@Valid @RequestBody CarrinhoRequestDTO carrinho) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(carrinho));
    }

    @Operation(summary = "Atualizar Carrinho")
    @PutMapping("/{id}")
    public CarrinhoResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody CarrinhoRequestDTO carrinho) {
        return service.atualizar(id, carrinho);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Excluir Carrinho")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
