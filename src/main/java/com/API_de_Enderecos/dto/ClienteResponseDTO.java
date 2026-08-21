package com.API_de_Enderecos.dto;

import com.API_de_Enderecos.entity.Cliente;

public record ClienteResponseDTO(

        Long id,
        String nome,
        String email,
        String telefone
) {

    public static ClienteResponseDTO fromEntity(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone()
        );
    }
}
