package com.ecommerceapi.mapper;

import com.ecommerceapi.dto.UsuarioRequestDTO;
import com.ecommerceapi.dto.UsuarioResponseDTO;
import com.ecommerceapi.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioRequestDTO dto);

    UsuarioResponseDTO toResponseDTO(Usuario entity);
}
