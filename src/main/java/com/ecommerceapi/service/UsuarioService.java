package com.ecommerceapi.service;

import com.ecommerceapi.dto.UsuarioRequestDTO;
import com.ecommerceapi.dto.UsuarioResponseDTO;
import com.ecommerceapi.exception.ResourceNotFoundException;
import com.ecommerceapi.mapper.UsuarioMapper;
import com.ecommerceapi.model.Usuario;
import com.ecommerceapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private UsuarioMapper mapper;

    @Transactional(readOnly = true)
    public org.springframework.data.domain.Page<UsuarioResponseDTO> listar(String username, int page, int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size, org.springframework.data.domain.Sort.by("id").descending());
        if (username != null && !username.isBlank()) {
            return repository.findByUsernameContainingIgnoreCase(username, pageable)
                .map(mapper::toResponseDTO);
        }
        return repository.findAll(pageable).map(mapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscar(Long id) {
        Usuario entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado com id: " + id));
        return mapper.toResponseDTO(entity);
    }

    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        Usuario entity = mapper.toEntity(dto);
        Usuario salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario não encontrado com id: " + id);
        }
        Usuario entity = mapper.toEntity(dto);
        entity.setId(id);
        Usuario salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}
