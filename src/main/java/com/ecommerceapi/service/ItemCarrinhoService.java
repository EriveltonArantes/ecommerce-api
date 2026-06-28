package com.ecommerceapi.service;

import com.ecommerceapi.dto.ItemCarrinhoRequestDTO;
import com.ecommerceapi.dto.ItemCarrinhoResponseDTO;
import com.ecommerceapi.exception.ResourceNotFoundException;
import com.ecommerceapi.mapper.ItemCarrinhoMapper;
import com.ecommerceapi.model.ItemCarrinho;
import com.ecommerceapi.repository.ItemCarrinhoRepository;
import com.ecommerceapi.repository.CarrinhoRepository;
import com.ecommerceapi.model.Carrinho;
import com.ecommerceapi.repository.ProdutoRepository;
import com.ecommerceapi.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ItemCarrinhoService {

    @Autowired
    private ItemCarrinhoRepository repository;

    @Autowired
    private ItemCarrinhoMapper mapper;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional(readOnly = true)
    public org.springframework.data.domain.Page<ItemCarrinhoResponseDTO> listar(int page, int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size, org.springframework.data.domain.Sort.by("id").descending());
        return repository.findAll(pageable).map(mapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public ItemCarrinhoResponseDTO buscar(Long id) {
        ItemCarrinho entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("ItemCarrinho não encontrado com id: " + id));
        return mapper.toResponseDTO(entity);
    }

    public ItemCarrinhoResponseDTO criar(ItemCarrinhoRequestDTO dto) {
        ItemCarrinho entity = mapper.toEntity(dto);
        Carrinho carrinho = carrinhoRepository.findById(dto.getCarrinhoId())
            .orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado com id: " + dto.getCarrinhoId()));
        entity.setCarrinho(carrinho);
        Produto produto = produtoRepository.findById(dto.getProdutoId())
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + dto.getProdutoId()));
        entity.setProduto(produto);
        ItemCarrinho salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public ItemCarrinhoResponseDTO atualizar(Long id, ItemCarrinhoRequestDTO dto) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("ItemCarrinho não encontrado com id: " + id);
        }
        ItemCarrinho entity = mapper.toEntity(dto);
        entity.setId(id);
        Carrinho carrinho = carrinhoRepository.findById(dto.getCarrinhoId())
            .orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado com id: " + dto.getCarrinhoId()));
        entity.setCarrinho(carrinho);
        Produto produto = produtoRepository.findById(dto.getProdutoId())
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + dto.getProdutoId()));
        entity.setProduto(produto);
        ItemCarrinho salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("ItemCarrinho não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}
