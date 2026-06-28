package com.ecommerceapi.service;

import com.ecommerceapi.dto.UsuarioSistemaRequestDTO;
import com.ecommerceapi.dto.UsuarioSistemaResponseDTO;
import com.ecommerceapi.exception.ResourceNotFoundException;
import com.ecommerceapi.mapper.UsuarioSistemaMapper;
import com.ecommerceapi.model.UsuarioSistema;
import com.ecommerceapi.repository.UsuarioSistemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioSistemaService {

    @Autowired
    private UsuarioSistemaRepository repository;

    @Autowired
    private UsuarioSistemaMapper mapper;

    @Transactional(readOnly = true)
    public org.springframework.data.domain.Page<UsuarioSistemaResponseDTO> listar(String username, int page, int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size, org.springframework.data.domain.Sort.by("id").descending());
        if (username != null && !username.isBlank()) {
            return repository.findByUsernameContainingIgnoreCase(username, pageable)
                .map(mapper::toResponseDTO);
        }
        return repository.findAll(pageable).map(mapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public UsuarioSistemaResponseDTO buscar(Long id) {
        UsuarioSistema entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("UsuarioSistema não encontrado com id: " + id));
        return mapper.toResponseDTO(entity);
    }

    public UsuarioSistemaResponseDTO criar(UsuarioSistemaRequestDTO dto) {
        UsuarioSistema entity = mapper.toEntity(dto);
        UsuarioSistema salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public UsuarioSistemaResponseDTO atualizar(Long id, UsuarioSistemaRequestDTO dto) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("UsuarioSistema não encontrado com id: " + id);
        }
        UsuarioSistema entity = mapper.toEntity(dto);
        entity.setId(id);
        UsuarioSistema salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("UsuarioSistema não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}
