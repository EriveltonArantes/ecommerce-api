package com.ecommerceapi.repository;

import com.ecommerceapi.model.UsuarioSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioSistemaRepository extends JpaRepository<UsuarioSistema, Long> {
    Optional<UsuarioSistema> findByUsername(String username);
    org.springframework.data.domain.Page<UsuarioSistema> findByUsernameContainingIgnoreCase(String username, org.springframework.data.domain.Pageable pageable);
}
