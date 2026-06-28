package com.ecommerceapi.dto;

import jakarta.validation.constraints.*;

public class ItemPedidoVendaRequestDTO {

    @NotNull(message = "PedidoId é obrigatório")
    @Positive(message = "PedidoId deve ser um ID válido (positivo)")
    private Long pedidoId;
    @NotNull(message = "ProdutoId é obrigatório")
    @Positive(message = "ProdutoId deve ser um ID válido (positivo)")
    private Long produtoId;
    @Min(value = 0, message = "quantidade não pode ser negativo")
    @NotNull(message = "quantidade não pode ser nulo")
    private Integer quantidade;
    @DecimalMin(value = "0.0", message = "preco unitario não pode ser negativo")
    @NotNull(message = "preco unitario não pode ser nulo")
    private java.math.BigDecimal precoUnitario;

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public java.math.BigDecimal getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(java.math.BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }
}
