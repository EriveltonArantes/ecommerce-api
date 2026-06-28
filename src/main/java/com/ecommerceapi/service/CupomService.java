package com.ecommerceapi.service;

import com.ecommerceapi.dto.CupomRequestDTO;
import com.ecommerceapi.dto.CupomResponseDTO;
import com.ecommerceapi.exception.ResourceNotFoundException;
import com.ecommerceapi.mapper.CupomMapper;
import com.ecommerceapi.model.Cupom;
import com.ecommerceapi.repository.CupomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CupomService {

    @Autowired
    private CupomRepository repository;

    @Autowired
    private CupomMapper mapper;

    @Transactional(readOnly = true)
    public org.springframework.data.domain.Page<CupomResponseDTO> listar(String codigo, int page, int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size, org.springframework.data.domain.Sort.by("id").descending());
        if (codigo != null && !codigo.isBlank()) {
            return repository.findByCodigoContainingIgnoreCase(codigo, pageable)
                .map(mapper::toResponseDTO);
        }
        return repository.findAll(pageable).map(mapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public CupomResponseDTO buscar(Long id) {
        Cupom entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cupom não encontrado com id: " + id));
        return mapper.toResponseDTO(entity);
    }

    public CupomResponseDTO criar(CupomRequestDTO dto) {
        Cupom entity = mapper.toEntity(dto);
        Cupom salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public CupomResponseDTO atualizar(Long id, CupomRequestDTO dto) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Cupom não encontrado com id: " + id);
        }
        Cupom entity = mapper.toEntity(dto);
        entity.setId(id);
        Cupom salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Cupom não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}
