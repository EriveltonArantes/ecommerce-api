package com.ecommerceapi.controller;

import com.ecommerceapi.dto.ProdutoRequestDTO;
import com.ecommerceapi.dto.ProdutoResponseDTO;
import com.ecommerceapi.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Produto", description = "Gerenciamento de produtos")
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Operation(summary = "Listar todos os Produto")
    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<ProdutoResponseDTO>> listar(@RequestParam(required = false) String descricao, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, @RequestParam(required = false) Long clienteId) {
        org.springframework.data.domain.Page<ProdutoResponseDTO> resultado = service.listar(descricao, page, size);
        if (clienteId != null) {
            java.util.List<ProdutoResponseDTO> filtrado = resultado.getContent().stream()
                .filter(item -> clienteId.equals(item.getClienteId()))
                .collect(java.util.stream.Collectors.toList());
            resultado = new org.springframework.data.domain.PageImpl<>(
                filtrado, org.springframework.data.domain.PageRequest.of(page, size), filtrado.size());
        }
        return ResponseEntity.ok(resultado);
    }

    @Operation(summary = "Buscar Produto por ID")
    @GetMapping("/{id}")
    public ProdutoResponseDTO buscar(@PathVariable Long id) { return service.buscar(id); }

    @Operation(summary = "Criar Produto")
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(@Valid @RequestBody ProdutoRequestDTO produto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(produto));
    }

    @Operation(summary = "Atualizar Produto")
    @PutMapping("/{id}")
    public ProdutoResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoRequestDTO produto) {
        return service.atualizar(id, produto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Excluir Produto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
