package com.API_de_Enderecos.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoRequestDTO {

    @NotBlank(message = "A rua é obrigatória")
    @Size(max = 100, message = "A rua deve ter no máximo 100 caracteres")
    private String rua;

    @NotNull(message = "O número é obrigatório")
    private Integer numero;

    @Size(max = 100, message = "O complemento deve ter no máximo 100 caracteres")
    private String complemento;

    @NotBlank(message = "O bairro é obrigatório")
    @Size(max = 50, message = "O bairro deve ter no máximo 50 caracteres")
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória")
    @Size(max = 100, message = "A cidade deve ter no máximo 100 caracteres")
    private String cidade;

    @NotBlank(message = "O estado é obrigatório")
    @Size(min = 2, max = 2, message = "O estado deve ter exatamente 2 caracteres")
    private String estado;

    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP inválido. Use o formato 00000-000 ou 00000000")
    private String cep;

    @NotNull(message = "O ID do cliente é obrigatório")
    private Long clienteId;
}
