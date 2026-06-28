package com.ecommerceapi.controller;

import com.ecommerceapi.model.ItemPedidoVenda;
import com.ecommerceapi.repository.ItemPedidoVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/relatorio")
public class CurvaAbcController {

    @Autowired
    private ItemPedidoVendaRepository itemPedidoVendaRepository;

    @GetMapping("/curva-abc")
    public List<Map<String, Object>> curvaAbc() {
        Map<Long, Double> valorPorCatalogo = new LinkedHashMap<>();
        for (ItemPedidoVenda i : itemPedidoVendaRepository.findAll()) {
            if (i.getProduto() == null) continue;
            Long idCatalogo = i.getProduto().getId();
            double valor = (i.getQuantidade() == null ? 0.0 : ((Number)i.getQuantidade()).doubleValue()) * (i.getPrecoUnitario() == null ? 0.0 : ((Number)i.getPrecoUnitario()).doubleValue());
            valorPorCatalogo.merge(idCatalogo, valor, Double::sum);
        }
        double total = valorPorCatalogo.values().stream().mapToDouble(Double::doubleValue).sum();
        List<Map.Entry<Long, Double>> ordenado = valorPorCatalogo.entrySet().stream()
            .sorted((a, b) -> Double.compare(b.getValue(), a.getValue())).collect(Collectors.toList());
        List<Map<String, Object>> resultado = new ArrayList<>();
        double acumulado = 0;
        for (Map.Entry<Long, Double> e : ordenado) {
            acumulado += e.getValue();
            double percentualAcumulado = total == 0 ? 0 : (acumulado / total) * 100;
            String classe = percentualAcumulado <= 80 ? "A" : (percentualAcumulado <= 95 ? "B" : "C");
            Map<String, Object> linha = new LinkedHashMap<>();
            linha.put("produtoId", e.getKey());
            linha.put("valorTotal", e.getValue());
            linha.put("percentualAcumulado", percentualAcumulado);
            linha.put("classe", classe);
            resultado.add(linha);
        }
        return resultado;
    }
}
