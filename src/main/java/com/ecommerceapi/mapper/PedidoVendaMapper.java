package com.ecommerceapi.mapper;

import com.ecommerceapi.dto.PedidoVendaRequestDTO;
import com.ecommerceapi.dto.PedidoVendaResponseDTO;
import com.ecommerceapi.model.PedidoVenda;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PedidoVendaMapper {

    PedidoVenda toEntity(PedidoVendaRequestDTO dto);

    PedidoVendaResponseDTO toResponseDTO(PedidoVenda entity);
}
