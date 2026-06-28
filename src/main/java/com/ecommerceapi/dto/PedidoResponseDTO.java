package com.ecommerceapi.dto;

public class PedidoResponseDTO {

    private Long id;
    private java.time.LocalDateTime criadoEm;
    private java.time.LocalDateTime atualizadoEm;
    private Long produtoId;
    private Long clienteId;
    private java.time.LocalDateTime data;
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public java.time.LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(java.time.LocalDateTime v) { this.criadoEm = v; }
    public java.time.LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(java.time.LocalDateTime v) { this.atualizadoEm = v; }
    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public java.time.LocalDateTime getData() { return data; }
    public void setData(java.time.LocalDateTime data) { this.data = data; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
