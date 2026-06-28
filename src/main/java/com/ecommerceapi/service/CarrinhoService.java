package com.ecommerceapi.service;

import com.ecommerceapi.dto.CarrinhoRequestDTO;
import com.ecommerceapi.dto.CarrinhoResponseDTO;
import com.ecommerceapi.exception.ResourceNotFoundException;
import com.ecommerceapi.mapper.CarrinhoMapper;
import com.ecommerceapi.model.Carrinho;
import com.ecommerceapi.repository.CarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository repository;

    @Autowired
    private CarrinhoMapper mapper;

    @Transactional(readOnly = true)
    public org.springframework.data.domain.Page<CarrinhoResponseDTO> listar(int page, int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size, org.springframework.data.domain.Sort.by("id").descending());
        return repository.findAll(pageable).map(mapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public CarrinhoResponseDTO buscar(Long id) {
        Carrinho entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado com id: " + id));
        return mapper.toResponseDTO(entity);
    }

    public CarrinhoResponseDTO criar(CarrinhoRequestDTO dto) {
        Carrinho entity = mapper.toEntity(dto);
        Carrinho salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public CarrinhoResponseDTO atualizar(Long id, CarrinhoRequestDTO dto) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Carrinho não encontrado com id: " + id);
        }
        Carrinho entity = mapper.toEntity(dto);
        entity.setId(id);
        Carrinho salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Carrinho não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}
