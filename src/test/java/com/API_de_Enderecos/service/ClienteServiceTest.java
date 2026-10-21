package com.API_de_Enderecos.service;

import com.API_de_Enderecos.dto.ClienteRequestDTO;
import com.API_de_Enderecos.dto.ClienteResponseDTO;
import com.API_de_Enderecos.entity.Cliente;
import com.API_de_Enderecos.exception.ClienteNaoEncontradoException;
import com.API_de_Enderecos.exception.EmailJaCadastradoException;
import com.API_de_Enderecos.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;
    private ClienteRequestDTO clienteRequestDTO;
    private ClienteResponseDTO clienteResponseDTO;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setEmail("joao@email.com");
        cliente.setTelefone("11999999999");

        clienteRequestDTO = new ClienteRequestDTO();
        clienteRequestDTO.setNome("João Silva");
        clienteRequestDTO.setEmail("joao@email.com");
        clienteRequestDTO.setTelefone("11999999999");

        clienteResponseDTO = new ClienteResponseDTO(
                1L,
                "João Silva",
                "joao@email.com",
                "11999999999"
        );
    }

    @Test
    void cadastrar_DeveRetornarClienteResponseDTO_QuandoEmailNaoExiste() {
        when(clienteRepository.existsByEmail(anyString())).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteResponseDTO result = clienteService.cadastrar(clienteRequestDTO);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("João Silva", result.nome());
        assertEquals("joao@email.com", result.email());
        assertEquals("11999999999", result.telefone());

        verify(clienteRepository, times(1)).existsByEmail("joao@email.com");
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void cadastrar_DeveLancarEmailJaCadastradoException_QuandoEmailJaExiste() {
        when(clienteRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(EmailJaCadastradoException.class, () -> clienteService.cadastrar(clienteRequestDTO));

        verify(clienteRepository, times(1)).existsByEmail("joao@email.com");
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void listar_DeveRetornarListaDeClienteResponseDTO() {
        List<Cliente> clientes = Arrays.asList(cliente);
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<ClienteResponseDTO> result = clienteService.listar();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).id());
        assertEquals("João Silva", result.get(0).nome());

        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_DeveRetornarClienteResponseDTO_QuandoClienteExiste() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        ClienteResponseDTO result = clienteService.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("João Silva", result.nome());

        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_DeveLancarClienteNaoEncontradoException_QuandoClienteNaoExiste() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> clienteService.buscarPorId(1L));

        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    void atualizar_DeveRetornarClienteResponseDTO_QuandoClienteExisteEEmailNaoMudou() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteResponseDTO result = clienteService.atualizar(1L, clienteRequestDTO);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("João Silva", result.nome());

        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void atualizar_DeveLancarEmailJaCadastradoException_QuandoEmailJaExiste() {
        ClienteRequestDTO novoEmailDTO = new ClienteRequestDTO();
        novoEmailDTO.setNome("João Silva");
        novoEmailDTO.setEmail("outro@email.com");
        novoEmailDTO.setTelefone("11999999999");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.existsByEmail("outro@email.com")).thenReturn(true);

        assertThrows(EmailJaCadastradoException.class, () -> clienteService.atualizar(1L, novoEmailDTO));

        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).existsByEmail("outro@email.com");
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void atualizar_DeveLancarClienteNaoEncontradoException_QuandoClienteNaoExiste() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> clienteService.atualizar(1L, clienteRequestDTO));

        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void deletar_DeveDeletarCliente_QuandoClienteExiste() {
        when(clienteRepository.existsById(1L)).thenReturn(true);
        doNothing().when(clienteRepository).deleteById(1L);

        clienteService.deletar(1L);

        verify(clienteRepository, times(1)).existsById(1L);
        verify(clienteRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletar_DeveLancarClienteNaoEncontradoException_QuandoClienteNaoExiste() {
        when(clienteRepository.existsById(1L)).thenReturn(false);

        assertThrows(ClienteNaoEncontradoException.class, () -> clienteService.deletar(1L));

        verify(clienteRepository, times(1)).existsById(1L);
        verify(clienteRepository, never()).deleteById(anyLong());
    }
}
