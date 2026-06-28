package com.ecommerceapi.mapper;

import com.ecommerceapi.dto.ItemPedidoVendaRequestDTO;
import com.ecommerceapi.dto.ItemPedidoVendaResponseDTO;
import com.ecommerceapi.model.ItemPedidoVenda;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemPedidoVendaMapper {

    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "produto", ignore = true)
    ItemPedidoVenda toEntity(ItemPedidoVendaRequestDTO dto);

    @Mapping(target = "pedidoId", source = "pedido.id")
    @Mapping(target = "produtoId", source = "produto.id")
    ItemPedidoVendaResponseDTO toResponseDTO(ItemPedidoVenda entity);
}
