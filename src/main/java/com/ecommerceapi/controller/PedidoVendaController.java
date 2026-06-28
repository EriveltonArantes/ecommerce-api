package com.ecommerceapi.controller;

import com.ecommerceapi.dto.PedidoVendaRequestDTO;
import com.ecommerceapi.dto.PedidoVendaResponseDTO;
import com.ecommerceapi.service.PedidoVendaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "PedidoVenda", description = "Gerenciamento de pedidovendas")
@RestController
@RequestMapping("/api/pedidovendas")
public class PedidoVendaController {

    @Autowired
    private PedidoVendaService service;

    @Operation(summary = "Listar todos os PedidoVenda")
    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<PedidoVendaResponseDTO>> listar(@RequestParam(required = false) String cupomCodigo, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.listar(cupomCodigo, page, size));
    }

    @Operation(summary = "Buscar PedidoVenda por ID")
    @GetMapping("/{id}")
    public PedidoVendaResponseDTO buscar(@PathVariable Long id) { return service.buscar(id); }

    @Operation(summary = "Criar PedidoVenda")
    @PostMapping
    public ResponseEntity<PedidoVendaResponseDTO> criar(@Valid @RequestBody PedidoVendaRequestDTO pedidoVenda) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(pedidoVenda));
    }

    @Operation(summary = "Atualizar PedidoVenda")
    @PutMapping("/{id}")
    public PedidoVendaResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody PedidoVendaRequestDTO pedidoVenda) {
        return service.atualizar(id, pedidoVenda);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Excluir PedidoVenda")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
