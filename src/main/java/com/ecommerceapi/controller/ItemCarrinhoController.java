package com.ecommerceapi.controller;

import com.ecommerceapi.dto.ItemCarrinhoRequestDTO;
import com.ecommerceapi.dto.ItemCarrinhoResponseDTO;
import com.ecommerceapi.service.ItemCarrinhoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ItemCarrinho", description = "Gerenciamento de itemcarrinhos")
@RestController
@RequestMapping("/api/itemcarrinhos")
public class ItemCarrinhoController {

    @Autowired
    private ItemCarrinhoService service;

    @Operation(summary = "Listar todos os ItemCarrinho")
    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<ItemCarrinhoResponseDTO>> listar(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, @RequestParam(required = false) Long carrinhoId, @RequestParam(required = false) Long produtoId) {
        org.springframework.data.domain.Page<ItemCarrinhoResponseDTO> resultado = service.listar(page, size);
        if (carrinhoId != null) {
            java.util.List<ItemCarrinhoResponseDTO> filtrado = resultado.getContent().stream()
                .filter(item -> carrinhoId.equals(item.getCarrinhoId()))
                .collect(java.util.stream.Collectors.toList());
            resultado = new org.springframework.data.domain.PageImpl<>(
                filtrado, org.springframework.data.domain.PageRequest.of(page, size), filtrado.size());
        }
        if (produtoId != null) {
            java.util.List<ItemCarrinhoResponseDTO> filtrado = resultado.getContent().stream()
                .filter(item -> produtoId.equals(item.getProdutoId()))
                .collect(java.util.stream.Collectors.toList());
            resultado = new org.springframework.data.domain.PageImpl<>(
                filtrado, org.springframework.data.domain.PageRequest.of(page, size), filtrado.size());
        }
        return ResponseEntity.ok(resultado);
    }

    @Operation(summary = "Buscar ItemCarrinho por ID")
    @GetMapping("/{id}")
    public ItemCarrinhoResponseDTO buscar(@PathVariable Long id) { return service.buscar(id); }

    @Operation(summary = "Criar ItemCarrinho")
    @PostMapping
    public ResponseEntity<ItemCarrinhoResponseDTO> criar(@Valid @RequestBody ItemCarrinhoRequestDTO itemCarrinho) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(itemCarrinho));
    }

    @Operation(summary = "Atualizar ItemCarrinho")
    @PutMapping("/{id}")
    public ItemCarrinhoResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody ItemCarrinhoRequestDTO itemCarrinho) {
        return service.atualizar(id, itemCarrinho);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Excluir ItemCarrinho")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
