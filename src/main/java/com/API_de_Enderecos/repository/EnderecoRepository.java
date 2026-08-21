package com.API_de_Enderecos.repository;

import com.API_de_Enderecos.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>,
        JpaSpecificationExecutor<Endereco> {
}
