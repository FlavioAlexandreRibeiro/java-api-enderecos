package com.API_de_Enderecos.service;

import com.API_de_Enderecos.dto.EnderecoRequestDTO;
import com.API_de_Enderecos.dto.EnderecoResponseDTO;
import com.API_de_Enderecos.entity.Cliente;
import com.API_de_Enderecos.entity.Endereco;
import com.API_de_Enderecos.exception.ClienteNaoEncontradoException;
import com.API_de_Enderecos.exception.EnderecoNaoEncontradoException;
import com.API_de_Enderecos.repository.ClienteRepository;
import com.API_de_Enderecos.repository.EnderecoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private EnderecoService enderecoService;

    private Cliente cliente;
    private Endereco endereco;
    private EnderecoRequestDTO enderecoRequestDTO;
    private EnderecoResponseDTO enderecoResponseDTO;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setEmail("joao@email.com");

        endereco = new Endereco();
        endereco.setId(1L);
        endereco.setRua("Rua das Flores");
        endereco.setNumero(123);
        endereco.setComplemento("Apto 101");
        endereco.setBairro("Centro");
        endereco.setCidade("São Paulo");
        endereco.setEstado("SP");
        endereco.setCep("01234-567");
        endereco.setCliente(cliente);

        enderecoRequestDTO = new EnderecoRequestDTO();
        enderecoRequestDTO.setRua("Rua das Flores");
        enderecoRequestDTO.setNumero(123);
        enderecoRequestDTO.setComplemento("Apto 101");
        enderecoRequestDTO.setBairro("Centro");
        enderecoRequestDTO.setCidade("São Paulo");
        enderecoRequestDTO.setEstado("SP");
        enderecoRequestDTO.setCep("01234-567");
        enderecoRequestDTO.setClienteId(1L);

        enderecoResponseDTO = new EnderecoResponseDTO(
                1L,
                "Rua das Flores",
                123,
                "Apto 101",
                "Centro",
                "São Paulo",
                "SP",
                "01234-567",
                1L
        );

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void cadastrar_DeveRetornarEnderecoResponseDTO_QuandoClienteExiste() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);

        EnderecoResponseDTO result = enderecoService.cadastrar(enderecoRequestDTO);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Rua das Flores", result.rua());
        assertEquals(123, result.numero());
        assertEquals(1L, result.clienteId());

        verify(clienteRepository, times(1)).findById(1L);
        verify(enderecoRepository, times(1)).save(any(Endereco.class));
    }

    @Test
    void cadastrar_DeveLancarClienteNaoEncontradoException_QuandoClienteNaoExiste() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> enderecoService.cadastrar(enderecoRequestDTO));

        verify(clienteRepository, times(1)).findById(1L);
        verify(enderecoRepository, never()).save(any(Endereco.class));
    }

    @Test
    void listar_DeveRetornarPaginaDeEnderecoResponseDTO() {
        Page<Endereco> pagina = new PageImpl<>(Arrays.asList(endereco));
        when(enderecoRepository.findAll(isA(Specification.class), any(Pageable.class))).thenReturn(pagina);

        Page<EnderecoResponseDTO> result = enderecoService.listar(
                null, null, null, null, null, null, null, null, pageable
        );

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1L, result.getContent().get(0).id());

        verify(enderecoRepository, times(1)).findAll(isA(Specification.class), any(Pageable.class));
    }

    @Test
    void listar_DeveRetornarPaginaVazia_QuandoNaoHaEnderecos() {
        Page<Endereco> paginaVazia = new PageImpl<>(Arrays.asList());
        when(enderecoRepository.findAll(isA(Specification.class), any(Pageable.class))).thenReturn(paginaVazia);

        Page<EnderecoResponseDTO> result = enderecoService.listar(
                null, null, null, null, null, null, null, null, pageable
        );

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());

        verify(enderecoRepository, times(1)).findAll(isA(Specification.class), any(Pageable.class));
    }

    @Test
    void buscarPorId_DeveRetornarEnderecoResponseDTO_QuandoEnderecoExiste() {
        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));

        EnderecoResponseDTO result = enderecoService.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Rua das Flores", result.rua());

        verify(enderecoRepository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_DeveLancarEnderecoNaoEncontradoException_QuandoEnderecoNaoExiste() {
        when(enderecoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EnderecoNaoEncontradoException.class, () -> enderecoService.buscarPorId(1L));

        verify(enderecoRepository, times(1)).findById(1L);
    }

    @Test
    void atualizar_DeveRetornarEnderecoResponseDTO_QuandoEnderecoEClienteExistem() {
        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);

        EnderecoResponseDTO result = enderecoService.atualizar(1L, enderecoRequestDTO);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Rua das Flores", result.rua());

        verify(enderecoRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).findById(1L);
        verify(enderecoRepository, times(1)).save(any(Endereco.class));
    }

    @Test
    void atualizar_DeveLancarEnderecoNaoEncontradoException_QuandoEnderecoNaoExiste() {
        when(enderecoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EnderecoNaoEncontradoException.class, () -> enderecoService.atualizar(1L, enderecoRequestDTO));

        verify(enderecoRepository, times(1)).findById(1L);
        verify(clienteRepository, never()).findById(anyLong());
        verify(enderecoRepository, never()).save(any(Endereco.class));
    }

    @Test
    void atualizar_DeveLancarClienteNaoEncontradoException_QuandoClienteNaoExiste() {
        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> enderecoService.atualizar(1L, enderecoRequestDTO));

        verify(enderecoRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).findById(1L);
        verify(enderecoRepository, never()).save(any(Endereco.class));
    }

    @Test
    void deletar_DeveDeletarEndereco_QuandoEnderecoExiste() {
        when(enderecoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(enderecoRepository).deleteById(1L);

        enderecoService.deletar(1L);

        verify(enderecoRepository, times(1)).existsById(1L);
        verify(enderecoRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletar_DeveLancarEnderecoNaoEncontradoException_QuandoEnderecoNaoExiste() {
        when(enderecoRepository.existsById(1L)).thenReturn(false);

        assertThrows(EnderecoNaoEncontradoException.class, () -> enderecoService.deletar(1L));

        verify(enderecoRepository, times(1)).existsById(1L);
        verify(enderecoRepository, never()).deleteById(anyLong());
    }
}
