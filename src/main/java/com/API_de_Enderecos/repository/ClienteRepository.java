package com.API_de_Enderecos.repository;

import com.API_de_Enderecos.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByEmail(String email);

    Optional<Cliente> findByEmail(String email);
}
