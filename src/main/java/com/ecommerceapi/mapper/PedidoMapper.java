package com.ecommerceapi.mapper;

import com.ecommerceapi.dto.PedidoRequestDTO;
import com.ecommerceapi.dto.PedidoResponseDTO;
import com.ecommerceapi.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    Pedido toEntity(PedidoRequestDTO dto);

    @Mapping(target = "produtoId", source = "produto.id")
    @Mapping(target = "clienteId", source = "cliente.id")
    PedidoResponseDTO toResponseDTO(Pedido entity);
}
