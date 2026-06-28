package com.ecommerceapi.mapper;

import com.ecommerceapi.dto.CarrinhoRequestDTO;
import com.ecommerceapi.dto.CarrinhoResponseDTO;
import com.ecommerceapi.model.Carrinho;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarrinhoMapper {

    Carrinho toEntity(CarrinhoRequestDTO dto);

    CarrinhoResponseDTO toResponseDTO(Carrinho entity);
}
