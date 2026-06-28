package com.ecommerceapi.repository;

import com.ecommerceapi.model.Cupom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CupomRepository extends JpaRepository<Cupom, Long> {

    org.springframework.data.domain.Page<Cupom> findByCodigoContainingIgnoreCase(String codigo, org.springframework.data.domain.Pageable pageable);
}
