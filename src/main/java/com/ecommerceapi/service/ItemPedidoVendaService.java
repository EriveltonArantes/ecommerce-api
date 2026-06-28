package com.ecommerceapi.service;

import com.ecommerceapi.dto.ItemPedidoVendaRequestDTO;
import com.ecommerceapi.dto.ItemPedidoVendaResponseDTO;
import com.ecommerceapi.exception.ResourceNotFoundException;
import com.ecommerceapi.mapper.ItemPedidoVendaMapper;
import com.ecommerceapi.model.ItemPedidoVenda;
import com.ecommerceapi.repository.ItemPedidoVendaRepository;
import com.ecommerceapi.repository.PedidoVendaRepository;
import com.ecommerceapi.model.PedidoVenda;
import com.ecommerceapi.repository.ProdutoRepository;
import com.ecommerceapi.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ItemPedidoVendaService {

    @Autowired
    private ItemPedidoVendaRepository repository;

    @Autowired
    private ItemPedidoVendaMapper mapper;

    @Autowired
    private PedidoVendaRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional(readOnly = true)
    public org.springframework.data.domain.Page<ItemPedidoVendaResponseDTO> listar(int page, int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size, org.springframework.data.domain.Sort.by("id").descending());
        return repository.findAll(pageable).map(mapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public ItemPedidoVendaResponseDTO buscar(Long id) {
        ItemPedidoVenda entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("ItemPedidoVenda não encontrado com id: " + id));
        return mapper.toResponseDTO(entity);
    }

    public ItemPedidoVendaResponseDTO criar(ItemPedidoVendaRequestDTO dto) {
        ItemPedidoVenda entity = mapper.toEntity(dto);
        PedidoVenda pedido = pedidoRepository.findById(dto.getPedidoId())
            .orElseThrow(() -> new ResourceNotFoundException("PedidoVenda não encontrado com id: " + dto.getPedidoId()));
        entity.setPedido(pedido);
        Produto produto = produtoRepository.findById(dto.getProdutoId())
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + dto.getProdutoId()));
        entity.setProduto(produto);
        ItemPedidoVenda salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public ItemPedidoVendaResponseDTO atualizar(Long id, ItemPedidoVendaRequestDTO dto) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("ItemPedidoVenda não encontrado com id: " + id);
        }
        ItemPedidoVenda entity = mapper.toEntity(dto);
        entity.setId(id);
        PedidoVenda pedido = pedidoRepository.findById(dto.getPedidoId())
            .orElseThrow(() -> new ResourceNotFoundException("PedidoVenda não encontrado com id: " + dto.getPedidoId()));
        entity.setPedido(pedido);
        Produto produto = produtoRepository.findById(dto.getProdutoId())
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + dto.getProdutoId()));
        entity.setProduto(produto);
        ItemPedidoVenda salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("ItemPedidoVenda não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}
