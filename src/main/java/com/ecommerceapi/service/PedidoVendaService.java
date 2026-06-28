package com.ecommerceapi.service;

import com.ecommerceapi.dto.PedidoVendaRequestDTO;
import com.ecommerceapi.dto.PedidoVendaResponseDTO;
import com.ecommerceapi.exception.ResourceNotFoundException;
import com.ecommerceapi.mapper.PedidoVendaMapper;
import com.ecommerceapi.model.PedidoVenda;
import com.ecommerceapi.repository.PedidoVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PedidoVendaService {

    @Autowired
    private PedidoVendaRepository repository;

    @Autowired
    private PedidoVendaMapper mapper;

    @Transactional(readOnly = true)
    public org.springframework.data.domain.Page<PedidoVendaResponseDTO> listar(String cupomCodigo, int page, int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size, org.springframework.data.domain.Sort.by("id").descending());
        if (cupomCodigo != null && !cupomCodigo.isBlank()) {
            return repository.findByCupomCodigoContainingIgnoreCase(cupomCodigo, pageable)
                .map(mapper::toResponseDTO);
        }
        return repository.findAll(pageable).map(mapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public PedidoVendaResponseDTO buscar(Long id) {
        PedidoVenda entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("PedidoVenda não encontrado com id: " + id));
        return mapper.toResponseDTO(entity);
    }

    public PedidoVendaResponseDTO criar(PedidoVendaRequestDTO dto) {
        PedidoVenda entity = mapper.toEntity(dto);
        PedidoVenda salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public PedidoVendaResponseDTO atualizar(Long id, PedidoVendaRequestDTO dto) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("PedidoVenda não encontrado com id: " + id);
        }
        PedidoVenda entity = mapper.toEntity(dto);
        entity.setId(id);
        PedidoVenda salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("PedidoVenda não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}
