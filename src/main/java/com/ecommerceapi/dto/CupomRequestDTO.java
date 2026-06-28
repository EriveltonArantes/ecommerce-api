package com.ecommerceapi.dto;

import jakarta.validation.constraints.*;

public class CupomRequestDTO {

    @NotBlank(message = "codigo não pode estar em branco")
    private String codigo;
    @NotBlank(message = "tipo desconto não pode estar em branco")
    private String tipoDesconto;
    @DecimalMin(value = "0.0", message = "valor desconto não pode ser negativo")
    @NotNull(message = "valor desconto não pode ser nulo")
    private Double valorDesconto;

    private java.time.LocalDate validade;
    @NotNull(message = "ativo não pode ser nulo")
    private Boolean ativo;

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getTipoDesconto() { return tipoDesconto; }
    public void setTipoDesconto(String tipoDesconto) { this.tipoDesconto = tipoDesconto; }
    public Double getValorDesconto() { return valorDesconto; }
    public void setValorDesconto(Double valorDesconto) { this.valorDesconto = valorDesconto; }
    public java.time.LocalDate getValidade() { return validade; }
    public void setValidade(java.time.LocalDate validade) { this.validade = validade; }
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}
