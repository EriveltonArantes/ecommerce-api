package com.ecommerceapi.controller;

import com.ecommerceapi.model.Carrinho;
import com.ecommerceapi.model.ItemCarrinho;
import com.ecommerceapi.model.Produto;
import com.ecommerceapi.model.Cupom;
import com.ecommerceapi.model.PedidoVenda;
import com.ecommerceapi.model.ItemPedidoVenda;
import com.ecommerceapi.repository.ItemCarrinhoRepository;
import com.ecommerceapi.repository.CupomRepository;
import com.ecommerceapi.repository.PedidoVendaRepository;
import com.ecommerceapi.repository.ItemPedidoVendaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Tag(name = "Checkout", description = "Finalização de compra: carrinho → pedido com cupom de desconto")
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    @Autowired private ItemCarrinhoRepository itemCarrinhoRepository;
    @Autowired private CupomRepository cupomRepository;
    @Autowired private PedidoVendaRepository pedidoVendaRepository;
    @Autowired private ItemPedidoVendaRepository itemPedidoVendaRepository;

    private java.math.BigDecimal precoDoItem(ItemCarrinho item) {
        Produto produto = item.getProduto();
        if (produto == null || produto.getPreco() == null) return java.math.BigDecimal.ZERO;
        int qtd = item.getQuantidade() == null ? 0 : item.getQuantidade();
        double precoVal = ((Number) produto.getPreco()).doubleValue();
        return java.math.BigDecimal.valueOf(precoVal * qtd);
    }

    @Operation(summary = "Resumo do carrinho com subtotal")
    @GetMapping("/carrinho/{carrinhoId}")
    public Map<String, Object> resumoCarrinho(@PathVariable Long carrinhoId) {
        List<ItemCarrinho> itens = itemCarrinhoRepository.findAll().stream()
            .filter(i -> i.getCarrinho() != null && i.getCarrinho().getId().equals(carrinhoId))
            .collect(Collectors.toList());
        java.math.BigDecimal subtotal = itens.stream()
            .map(this::precoDoItem).reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
        Map<String, Object> r = new LinkedHashMap<>();
        r.put("itens", itens);
        r.put("subtotal", subtotal);
        return r;
    }

    @Operation(summary = "Finalizar compra: aplica cupom, cria pedido e limpa carrinho")
    @Transactional
    @PostMapping("/finalizar")
    public ResponseEntity<?> finalizar(@RequestBody Map<String, Object> body) {
        Object carrinhoIdRaw = body.get("carrinhoId");
        if (carrinhoIdRaw == null) return ResponseEntity.badRequest().body(Map.of("erro", "carrinhoId e obrigatorio"));
        Long carrinhoId = Long.valueOf(String.valueOf(carrinhoIdRaw));
        String cupomCodigo = body.get("cupomCodigo") == null ? null : String.valueOf(body.get("cupomCodigo")).trim();

        List<ItemCarrinho> itens = itemCarrinhoRepository.findAll().stream()
            .filter(i -> i.getCarrinho() != null && i.getCarrinho().getId().equals(carrinhoId))
            .collect(Collectors.toList());
        if (itens.isEmpty()) return ResponseEntity.badRequest().body(Map.of("erro", "carrinho vazio"));

        java.math.BigDecimal total = itens.stream()
            .map(this::precoDoItem).reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
        String cupomAplicado = null;
        if (cupomCodigo != null && !cupomCodigo.isEmpty()) {
            Optional<Cupom> cupomOpt = cupomRepository.findAll().stream()
                .filter(c -> cupomCodigo.equalsIgnoreCase(c.getCodigo())).findFirst();
            if (cupomOpt.isPresent()) {
                Cupom cupom = cupomOpt.get();
                boolean valido = Boolean.TRUE.equals(cupom.getAtivo())
                    && (cupom.getValidade() == null || !cupom.getValidade().isBefore(LocalDate.now()));
                if (valido) {
                    java.math.BigDecimal desconto = cupom.getValorDesconto() == null ? java.math.BigDecimal.ZERO : java.math.BigDecimal.valueOf(((Number) cupom.getValorDesconto()).doubleValue());
                    if ("PERCENTUAL".equalsIgnoreCase(cupom.getTipoDesconto())) {
                        java.math.BigDecimal fator = java.math.BigDecimal.ONE.subtract(desconto.divide(java.math.BigDecimal.valueOf(100), 4, java.math.RoundingMode.HALF_UP));
                        total = total.multiply(fator).setScale(2, java.math.RoundingMode.HALF_UP);
                    } else {
                        total = total.subtract(desconto);
                    }
                    if (total.compareTo(java.math.BigDecimal.ZERO) < 0) total = java.math.BigDecimal.ZERO;
                    cupomAplicado = cupom.getCodigo();
                }
            }
        }

        PedidoVenda pedido = new PedidoVenda();
        pedido.setStatus("NOVO");
        pedido.setTotal(total);
        pedido.setCupomCodigo(cupomAplicado);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido = pedidoVendaRepository.save(pedido);

        for (ItemCarrinho item : itens) {
            ItemPedidoVenda ip = new ItemPedidoVenda();
            ip.setPedido(pedido);
            ip.setProduto(item.getProduto());
            ip.setQuantidade(item.getQuantidade());
            Produto produtoRef = item.getProduto();
            ip.setPrecoUnitario(produtoRef != null && produtoRef.getPreco() != null ? java.math.BigDecimal.valueOf(((Number)produtoRef.getPreco()).doubleValue()) : java.math.BigDecimal.ZERO);
            itemPedidoVendaRepository.save(ip);
        }

        itemCarrinhoRepository.deleteAll(itens);
        return ResponseEntity.ok(pedido);
    }
}
