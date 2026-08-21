package com.API_de_Enderecos.service;

import com.API_de_Enderecos.dto.EnderecoRequestDTO;
import com.API_de_Enderecos.dto.EnderecoResponseDTO;
import com.API_de_Enderecos.entity.Cliente;
import com.API_de_Enderecos.entity.Endereco;
import com.API_de_Enderecos.repository.ClienteRepository;
import com.API_de_Enderecos.repository.EnderecoRepository;
import com.API_de_Enderecos.specification.EnderecoSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final ClienteRepository clienteRepository;


    public EnderecoResponseDTO cadastrar(EnderecoRequestDTO enderecoDTO) {

        Cliente cliente = clienteRepository.findById(enderecoDTO.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cliente não encontrado com o ID: " + enderecoDTO.getClienteId()));

        Endereco endereco = new Endereco();

        endereco.setRua(enderecoDTO.getRua());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setComplemento(enderecoDTO.getComplemento());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setEstado(enderecoDTO.getEstado());
        endereco.setCep(enderecoDTO.getCep());
        endereco.setCliente(cliente);

        Endereco enderecoSalvo = enderecoRepository.save(endereco);

        return EnderecoResponseDTO.fromEntity(enderecoSalvo);
    }


    public Page<EnderecoResponseDTO> listar(
            String rua,
            Integer numero,
            String complemento,
            String bairro,
            String cidade,
            String estado,
            String cep,
            Long clienteId,
            Pageable pageable) {

        Specification<Endereco> spec = (root, query,
                                        criteriaBuilder) ->
                criteriaBuilder.conjunction();

        if (Objects.nonNull(rua) && !rua.isBlank()) {
            spec = spec.and(EnderecoSpecification.rua(rua));
        }

        if (Objects.nonNull(numero)) {
            spec = spec.and(EnderecoSpecification.numero(numero));
        }

        if (Objects.nonNull(complemento)) {
            spec = spec.and(EnderecoSpecification.complemento(complemento));
        }

        if (Objects.nonNull(bairro)) {
            spec = spec.and(EnderecoSpecification.bairro(bairro));
        }

        if (Objects.nonNull(cidade)) {
            spec = spec.and(EnderecoSpecification.cidade(cidade));
        }

        if (Objects.nonNull(estado)) {
            spec = spec.and(EnderecoSpecification.estado(estado));
        }

        if (Objects.nonNull(cep)) {
            spec = spec.and(EnderecoSpecification.cep(cep));
        }

        if (Objects.nonNull(clienteId)) {
            spec = spec.and(EnderecoSpecification.clienteId(clienteId));
        }

        return enderecoRepository
                .findAll(spec, pageable)
                .map(EnderecoResponseDTO::fromEntity);
    }


    public EnderecoResponseDTO buscarPorId(Long id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Endereço não encontrado com o ID: " + id));

        return EnderecoResponseDTO.fromEntity(endereco);
    }


    public EnderecoResponseDTO atualizar(Long id, EnderecoRequestDTO enderecoDTO) {

        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Endereço não encontrado com o ID: " + id));

        Cliente cliente = clienteRepository.findById(enderecoDTO.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cliente não encontrado com o ID: " + enderecoDTO.getClienteId()));

        endereco.setRua(enderecoDTO.getRua());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setComplemento(enderecoDTO.getComplemento());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setEstado(enderecoDTO.getEstado());
        endereco.setCep(enderecoDTO.getCep());
        endereco.setCliente(cliente);

        Endereco enderecoAtualizado = enderecoRepository.save(endereco);

        return EnderecoResponseDTO.fromEntity(enderecoAtualizado);
    }


    public void deletar(Long id) {

        if (!enderecoRepository.existsById(id)) {
            throw new IllegalArgumentException("Endereço não encontrado com o ID: " + id);
        }

        enderecoRepository.deleteById(id);
    }
}