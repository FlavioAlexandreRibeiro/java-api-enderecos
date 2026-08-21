package com.API_de_Enderecos.service;

import com.API_de_Enderecos.dto.ClienteRequestDTO;
import com.API_de_Enderecos.dto.ClienteResponseDTO;
import com.API_de_Enderecos.entity.Cliente;
import com.API_de_Enderecos.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteResponseDTO cadastrar(ClienteRequestDTO clienteDTO) {

        if (clienteRepository.existsByEmail(clienteDTO.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Cliente cliente = new Cliente();

        cliente.setNome(clienteDTO.getNome());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefone(clienteDTO.getTelefone());

        Cliente clienteSalvo = clienteRepository.save(cliente);

        return ClienteResponseDTO.fromEntity(clienteSalvo);
    }


    public Page<ClienteResponseDTO> listar(Pageable pageable) {
        return clienteRepository.findAll(pageable)
                .map(ClienteResponseDTO::fromEntity);
    }

    public List<ClienteResponseDTO> listar() {
        return clienteRepository.findAll()
                .stream()
                .map(ClienteResponseDTO::fromEntity)
                .toList();
    }


    public ClienteResponseDTO buscarPorId(Long id) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com o ID: " + id));

        return ClienteResponseDTO.fromEntity(cliente);
    }


    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO clienteDTO) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com o ID: " + id));

        if (!cliente.getEmail().equals(clienteDTO.getEmail())
                && clienteRepository.existsByEmail(clienteDTO.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        cliente.setNome(clienteDTO.getNome());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefone(clienteDTO.getTelefone());

        Cliente clienteAtualizado = clienteRepository.save(cliente);

        return ClienteResponseDTO.fromEntity(clienteAtualizado);
    }


    public void deletar(Long id) {

        if (!clienteRepository.existsById(id)) {
            throw new IllegalArgumentException("Cliente não encontrado com o ID: " + id);
        }

        clienteRepository.deleteById(id);
    }
}
