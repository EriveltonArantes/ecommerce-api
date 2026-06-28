package com.ecommerceapi.mapper;

import com.ecommerceapi.dto.UsuarioSistemaRequestDTO;
import com.ecommerceapi.dto.UsuarioSistemaResponseDTO;
import com.ecommerceapi.model.UsuarioSistema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioSistemaMapper {

    UsuarioSistema toEntity(UsuarioSistemaRequestDTO dto);

    UsuarioSistemaResponseDTO toResponseDTO(UsuarioSistema entity);
}
