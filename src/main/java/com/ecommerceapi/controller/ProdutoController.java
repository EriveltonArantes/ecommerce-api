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
import java.util.List;

@Tag(name = "Produto", description = "Gerenciamento de produtos")
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Operation(summary = "Listar todos os Produto")
    @GetMapping
    public List<ProdutoResponseDTO> listar(@RequestParam(required = false) String descricao, @RequestParam(required = false) Long clienteId) {
        List<ProdutoResponseDTO> resultado = service.listar();
        if (descricao != null && !descricao.isBlank()) {
            resultado = resultado.stream().filter(item -> item.getDescricao() != null &&
                item.getDescricao().toLowerCase().contains(descricao.toLowerCase()))
                .collect(java.util.stream.Collectors.toList());
        }
        if (clienteId != null) {
            resultado = resultado.stream().filter(item -> clienteId.equals(item.getClienteId())).collect(java.util.stream.Collectors.toList());
        }
        return resultado;
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
