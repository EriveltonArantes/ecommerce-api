package com.ecommerceapi.service;

import com.ecommerceapi.dto.ClienteRequestDTO;
import com.ecommerceapi.dto.ClienteResponseDTO;
import com.ecommerceapi.exception.ResourceNotFoundException;
import com.ecommerceapi.mapper.ClienteMapper;
import com.ecommerceapi.model.Cliente;
import com.ecommerceapi.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private ClienteMapper mapper;

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listar() {
        return repository.findAll().stream().map(mapper::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscar(Long id) {
        Cliente entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + id));
        return mapper.toResponseDTO(entity);
    }

    public ClienteResponseDTO criar(ClienteRequestDTO dto) {
        Cliente entity = mapper.toEntity(dto);
        Cliente salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado com id: " + id);
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
