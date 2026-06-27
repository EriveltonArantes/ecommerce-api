package com.ecommerceapi.service;

import com.ecommerceapi.dto.PedidoRequestDTO;
import com.ecommerceapi.dto.PedidoResponseDTO;
import com.ecommerceapi.exception.ResourceNotFoundException;
import com.ecommerceapi.mapper.PedidoMapper;
import com.ecommerceapi.model.Pedido;
import com.ecommerceapi.repository.PedidoRepository;
import com.ecommerceapi.repository.ProdutoRepository;
import com.ecommerceapi.model.Produto;
import com.ecommerceapi.repository.ClienteRepository;
import com.ecommerceapi.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private PedidoMapper mapper;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listar() {
        return repository.findAll().stream().map(mapper::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PedidoResponseDTO buscar(Long id) {
        Pedido entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id: " + id));
        return mapper.toResponseDTO(entity);
    }

    public PedidoResponseDTO criar(PedidoRequestDTO dto) {
        Pedido entity = mapper.toEntity(dto);
        Produto produto = produtoRepository.findById(dto.getProdutoId())
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + dto.getProdutoId()));
        entity.setProduto(produto);
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + dto.getClienteId()));
        entity.setCliente(cliente);
        Pedido salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public PedidoResponseDTO atualizar(Long id, PedidoRequestDTO dto) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido não encontrado com id: " + id);
        }
        Pedido entity = mapper.toEntity(dto);
        entity.setId(id);
        Produto produto = produtoRepository.findById(dto.getProdutoId())
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + dto.getProdutoId()));
        entity.setProduto(produto);
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + dto.getClienteId()));
        entity.setCliente(cliente);
        Pedido salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}
