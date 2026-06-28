package com.ecommerceapi.controller;

import com.ecommerceapi.dto.ItemPedidoVendaRequestDTO;
import com.ecommerceapi.dto.ItemPedidoVendaResponseDTO;
import com.ecommerceapi.service.ItemPedidoVendaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ItemPedidoVenda", description = "Gerenciamento de itempedidovendas")
@RestController
@RequestMapping("/api/itempedidovendas")
public class ItemPedidoVendaController {

    @Autowired
    private ItemPedidoVendaService service;

    @Operation(summary = "Listar todos os ItemPedidoVenda")
    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<ItemPedidoVendaResponseDTO>> listar(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, @RequestParam(required = false) Long pedidoId, @RequestParam(required = false) Long produtoId) {
        org.springframework.data.domain.Page<ItemPedidoVendaResponseDTO> resultado = service.listar(page, size);
        if (pedidoId != null) {
            java.util.List<ItemPedidoVendaResponseDTO> filtrado = resultado.getContent().stream()
                .filter(item -> pedidoId.equals(item.getPedidoId()))
                .collect(java.util.stream.Collectors.toList());
            resultado = new org.springframework.data.domain.PageImpl<>(
                filtrado, org.springframework.data.domain.PageRequest.of(page, size), filtrado.size());
        }
        if (produtoId != null) {
            java.util.List<ItemPedidoVendaResponseDTO> filtrado = resultado.getContent().stream()
                .filter(item -> produtoId.equals(item.getProdutoId()))
                .collect(java.util.stream.Collectors.toList());
            resultado = new org.springframework.data.domain.PageImpl<>(
                filtrado, org.springframework.data.domain.PageRequest.of(page, size), filtrado.size());
        }
        return ResponseEntity.ok(resultado);
    }

    @Operation(summary = "Buscar ItemPedidoVenda por ID")
    @GetMapping("/{id}")
    public ItemPedidoVendaResponseDTO buscar(@PathVariable Long id) { return service.buscar(id); }

    @Operation(summary = "Criar ItemPedidoVenda")
    @PostMapping
    public ResponseEntity<ItemPedidoVendaResponseDTO> criar(@Valid @RequestBody ItemPedidoVendaRequestDTO itemPedidoVenda) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(itemPedidoVenda));
    }

    @Operation(summary = "Atualizar ItemPedidoVenda")
    @PutMapping("/{id}")
    public ItemPedidoVendaResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody ItemPedidoVendaRequestDTO itemPedidoVenda) {
        return service.atualizar(id, itemPedidoVenda);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Excluir ItemPedidoVenda")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
