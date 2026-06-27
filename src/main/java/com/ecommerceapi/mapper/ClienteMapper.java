package com.ecommerceapi.mapper;

import com.ecommerceapi.dto.ClienteRequestDTO;
import com.ecommerceapi.dto.ClienteResponseDTO;
import com.ecommerceapi.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    Cliente toEntity(ClienteRequestDTO dto);

    ClienteResponseDTO toResponseDTO(Cliente entity);
}
