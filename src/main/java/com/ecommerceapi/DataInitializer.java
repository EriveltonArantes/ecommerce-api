package com.ecommerceapi;

import com.ecommerceapi.model.UsuarioSistema;
import com.ecommerceapi.repository.UsuarioSistemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Autowired
    private UsuarioSistemaRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${ADMIN_USERNAME:admin}")
    private String adminUsername;

    @Value("${ADMIN_PASSWORD:admin123}")
    private String adminPassword;

    @Bean
    CommandLineRunner initAdmin() {
        return args -> {
            boolean adminExiste = repository.findAll().stream()
                .anyMatch(u -> "ADMIN".equals(u.getRole()));
            if (!adminExiste) {
                UsuarioSistema admin = new UsuarioSistema();
                admin.setUsername(adminUsername);
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRole("ADMIN");
                repository.save(admin);
            }
        };
    }
}
