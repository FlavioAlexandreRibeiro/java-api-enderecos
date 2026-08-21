package com.API_de_Enderecos.controller;

import com.API_de_Enderecos.dto.ClienteRequestDTO;
import com.API_de_Enderecos.dto.ClienteResponseDTO;
import com.API_de_Enderecos.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;


    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criar(
            @Valid @RequestBody ClienteRequestDTO clienteDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clienteService.cadastrar(clienteDTO));
    }


    @GetMapping
    public List<ClienteResponseDTO> listar() {
        return clienteService.listar();
    }


    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDTO clienteDTO) {
        return ResponseEntity.ok(clienteService.atualizar(id, clienteDTO));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
