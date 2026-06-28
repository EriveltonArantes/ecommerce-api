package com.ecommerceapi.dto;

import jakarta.validation.constraints.*;

public class CarrinhoRequestDTO {

    @NotBlank(message = "token não pode estar em branco")
    private String token;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
