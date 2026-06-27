package com.ecommerceapi.dto;

import jakarta.validation.constraints.*;

public class PedidoRequestDTO {

    @NotNull(message = "ProdutoId é obrigatório")
    @Positive(message = "ProdutoId deve ser um ID válido (positivo)")
    private Long produtoId;
    @NotNull(message = "ClienteId é obrigatório")
    @Positive(message = "ClienteId deve ser um ID válido (positivo)")
    private Long clienteId;
    @NotNull(message = "data não pode ser nulo")
    private java.time.LocalDateTime data;
    @NotBlank(message = "status não pode estar em branco")
    private String status;

    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public java.time.LocalDateTime getData() { return data; }
    public void setData(java.time.LocalDateTime data) { this.data = data; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
