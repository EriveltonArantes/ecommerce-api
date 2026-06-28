package com.ecommerceapi.mapper;

import com.ecommerceapi.dto.ItemCarrinhoRequestDTO;
import com.ecommerceapi.dto.ItemCarrinhoResponseDTO;
import com.ecommerceapi.model.ItemCarrinho;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemCarrinhoMapper {

    @Mapping(target = "carrinho", ignore = true)
    @Mapping(target = "produto", ignore = true)
    ItemCarrinho toEntity(ItemCarrinhoRequestDTO dto);

    @Mapping(target = "carrinhoId", source = "carrinho.id")
    @Mapping(target = "produtoId", source = "produto.id")
    ItemCarrinhoResponseDTO toResponseDTO(ItemCarrinho entity);
}
