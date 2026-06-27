package com.ecommerceapi.controller;

import com.ecommerceapi.dto.PedidoRequestDTO;
import com.ecommerceapi.dto.PedidoResponseDTO;
import com.ecommerceapi.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Pedido", description = "Gerenciamento de pedidos")
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @Operation(summary = "Listar todos os Pedido")
    @GetMapping
    public List<PedidoResponseDTO> listar(@RequestParam(required = false) Long produtoId, @RequestParam(required = false) Long clienteId) {
        List<PedidoResponseDTO> resultado = service.listar();
        if (produtoId != null) {
            resultado = resultado.stream().filter(item -> produtoId.equals(item.getProdutoId())).collect(java.util.stream.Collectors.toList());
        }
        if (clienteId != null) {
            resultado = resultado.stream().filter(item -> clienteId.equals(item.getClienteId())).collect(java.util.stream.Collectors.toList());
        }
        return resultado;
    }

    @Operation(summary = "Buscar Pedido por ID")
    @GetMapping("/{id}")
    public PedidoResponseDTO buscar(@PathVariable Long id) { return service.buscar(id); }

    @Operation(summary = "Criar Pedido")
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criar(@Valid @RequestBody PedidoRequestDTO pedido) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(pedido));
    }

    @Operation(summary = "Atualizar Pedido")
    @PutMapping("/{id}")
    public PedidoResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody PedidoRequestDTO pedido) {
        return service.atualizar(id, pedido);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Excluir Pedido")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
