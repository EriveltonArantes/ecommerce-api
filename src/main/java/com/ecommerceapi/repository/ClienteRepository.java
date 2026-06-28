package com.ecommerceapi.repository;

import com.ecommerceapi.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);

    org.springframework.data.domain.Page<Cliente> findByNomeContainingIgnoreCase(String nome, org.springframework.data.domain.Pageable pageable);
}
