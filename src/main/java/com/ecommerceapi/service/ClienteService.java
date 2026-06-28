package com.ecommerceapi.service;

import com.ecommerceapi.dto.ClienteRequestDTO;
import com.ecommerceapi.dto.ClienteResponseDTO;
import com.ecommerceapi.exception.ResourceNotFoundException;
import com.ecommerceapi.exception.BusinessException;
import com.ecommerceapi.mapper.ClienteMapper;
import com.ecommerceapi.model.Cliente;
import com.ecommerceapi.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private ClienteMapper mapper;

    @Transactional(readOnly = true)
    public org.springframework.data.domain.Page<ClienteResponseDTO> listar(String nome, int page, int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size, org.springframework.data.domain.Sort.by("id").descending());
        if (nome != null && !nome.isBlank()) {
            return repository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(mapper::toResponseDTO);
        }
        return repository.findAll(pageable).map(mapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscar(Long id) {
        Cliente entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + id));
        return mapper.toResponseDTO(entity);
    }

    public ClienteResponseDTO criar(ClienteRequestDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email já cadastrado: " + dto.getEmail());
        }
        Cliente entity = mapper.toEntity(dto);
        Cliente salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado com id: " + id);
        }
        if (repository.existsByEmailAndIdNot(dto.getEmail(), id)) {
            throw new BusinessException("Email já cadastrado em outro registro: " + dto.getEmail());
        }
        Cliente entity = mapper.toEntity(dto);
        entity.setId(id);
        Cliente salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}
