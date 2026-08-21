package com.API_de_Enderecos.dto;

import com.API_de_Enderecos.entity.Endereco;

public record EnderecoResponseDTO(
        Long id,
        String rua,
        Integer numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        String cep,
        Long clienteId
) {
    public static EnderecoResponseDTO fromEntity(Endereco endereco) {
        return new EnderecoResponseDTO(
                endereco.getId(),
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep(),
                endereco.getCliente() != null ? endereco.getCliente().getId() : null
        );
    }
}
