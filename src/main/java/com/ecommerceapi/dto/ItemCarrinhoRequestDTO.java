package com.ecommerceapi.dto;

import jakarta.validation.constraints.*;

public class ItemCarrinhoRequestDTO {

    @NotNull(message = "CarrinhoId é obrigatório")
    @Positive(message = "CarrinhoId deve ser um ID válido (positivo)")
    private Long carrinhoId;
    @NotNull(message = "ProdutoId é obrigatório")
    @Positive(message = "ProdutoId deve ser um ID válido (positivo)")
    private Long produtoId;
    @Min(value = 0, message = "quantidade não pode ser negativo")
    @NotNull(message = "quantidade não pode ser nulo")
    private Integer quantidade;

    public Long getCarrinhoId() { return carrinhoId; }
    public void setCarrinhoId(Long carrinhoId) { this.carrinhoId = carrinhoId; }
    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
}
