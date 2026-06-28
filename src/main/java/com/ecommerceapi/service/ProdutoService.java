package com.ecommerceapi.service;

import com.ecommerceapi.dto.ProdutoRequestDTO;
import com.ecommerceapi.dto.ProdutoResponseDTO;
import com.ecommerceapi.exception.ResourceNotFoundException;
import com.ecommerceapi.mapper.ProdutoMapper;
import com.ecommerceapi.model.Produto;
import com.ecommerceapi.repository.ProdutoRepository;
import com.ecommerceapi.repository.ClienteRepository;
import com.ecommerceapi.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private ProdutoMapper mapper;

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public org.springframework.data.domain.Page<ProdutoResponseDTO> listar(String descricao, int page, int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size, org.springframework.data.domain.Sort.by("id").descending());
        if (descricao != null && !descricao.isBlank()) {
            return repository.findByDescricaoContainingIgnoreCase(descricao, pageable)
                .map(mapper::toResponseDTO);
        }
        return repository.findAll(pageable).map(mapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public ProdutoResponseDTO buscar(Long id) {
        Produto entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + id));
        return mapper.toResponseDTO(entity);
    }

    public ProdutoResponseDTO criar(ProdutoRequestDTO dto) {
        Produto entity = mapper.toEntity(dto);
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + dto.getClienteId()));
        entity.setCliente(cliente);
        Produto salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado com id: " + id);
        }
        Produto entity = mapper.toEntity(dto);
        entity.setId(id);
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + dto.getClienteId()));
        entity.setCliente(cliente);
        Produto salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}
