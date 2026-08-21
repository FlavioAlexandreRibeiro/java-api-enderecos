package com.API_de_Enderecos.repository;

import com.API_de_Enderecos.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
