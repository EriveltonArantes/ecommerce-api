package com.ecommerceapi.dto;

import jakarta.validation.constraints.*;

public class PedidoVendaRequestDTO {

    @NotBlank(message = "status não pode estar em branco")
    private String status;
    @DecimalMin(value = "0.0", message = "total não pode ser negativo")
    @NotNull(message = "total não pode ser nulo")
    private java.math.BigDecimal total;
    @NotBlank(message = "cupom codigo não pode estar em branco")
    private String cupomCodigo;

    private java.time.LocalDateTime dataCriacao;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public java.math.BigDecimal getTotal() { return total; }
    public void setTotal(java.math.BigDecimal total) { this.total = total; }
    public String getCupomCodigo() { return cupomCodigo; }
    public void setCupomCodigo(String cupomCodigo) { this.cupomCodigo = cupomCodigo; }
    public java.time.LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(java.time.LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
}
