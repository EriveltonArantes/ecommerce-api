package com.ecommerceapi.repository;

import com.ecommerceapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    org.springframework.data.domain.Page<Usuario> findByUsernameContainingIgnoreCase(String username, org.springframework.data.domain.Pageable pageable);
}
