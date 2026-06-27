package com.ecommerceapi.mapper;

import com.ecommerceapi.dto.ProdutoRequestDTO;
import com.ecommerceapi.dto.ProdutoResponseDTO;
import com.ecommerceapi.model.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    @Mapping(target = "cliente", ignore = true)
    Produto toEntity(ProdutoRequestDTO dto);

    @Mapping(target = "clienteId", source = "cliente.id")
    ProdutoResponseDTO toResponseDTO(Produto entity);
}
