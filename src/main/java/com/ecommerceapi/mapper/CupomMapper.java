package com.ecommerceapi.mapper;

import com.ecommerceapi.dto.CupomRequestDTO;
import com.ecommerceapi.dto.CupomResponseDTO;
import com.ecommerceapi.model.Cupom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CupomMapper {

    Cupom toEntity(CupomRequestDTO dto);

    CupomResponseDTO toResponseDTO(Cupom entity);
}
