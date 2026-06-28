package com.ecommerceapi.repository;

import com.ecommerceapi.model.PedidoVenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoVendaRepository extends JpaRepository<PedidoVenda, Long> {

    org.springframework.data.domain.Page<PedidoVenda> findByCupomCodigoContainingIgnoreCase(String cupomCodigo, org.springframework.data.domain.Pageable pageable);
}
