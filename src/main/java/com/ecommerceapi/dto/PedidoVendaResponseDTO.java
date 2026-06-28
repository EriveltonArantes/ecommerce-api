package com.ecommerceapi.dto;

public class PedidoVendaResponseDTO {

    private Long id;
    private java.time.LocalDateTime criadoEm;
    private java.time.LocalDateTime atualizadoEm;
    private String status;
    private java.math.BigDecimal total;
    private String cupomCodigo;
    private java.time.LocalDateTime dataCriacao;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public java.time.LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(java.time.LocalDateTime v) { this.criadoEm = v; }
    public java.time.LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(java.time.LocalDateTime v) { this.atualizadoEm = v; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public java.math.BigDecimal getTotal() { return total; }
    public void setTotal(java.math.BigDecimal total) { this.total = total; }
    public String getCupomCodigo() { return cupomCodigo; }
    public void setCupomCodigo(String cupomCodigo) { this.cupomCodigo = cupomCodigo; }
    public java.time.LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(java.time.LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
}
