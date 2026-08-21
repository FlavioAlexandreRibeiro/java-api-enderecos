package com.API_de_Enderecos.controller;

import com.API_de_Enderecos.dto.EnderecoRequestDTO;
import com.API_de_Enderecos.dto.EnderecoResponseDTO;
import com.API_de_Enderecos.service.EnderecoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enderecos")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService enderecoService;

    @PostMapping
    public ResponseEntity<EnderecoResponseDTO> criar(
            @Valid @RequestBody EnderecoRequestDTO enderecoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(enderecoService.cadastrar(enderecoDTO));
    }


    @GetMapping
    public Page<EnderecoResponseDTO> listar(
            @RequestParam(required = false) String rua,
            @RequestParam(required = false) Integer numero,
            @RequestParam(required = false) String complemento,
            @RequestParam(required = false) String bairro,
            @RequestParam(required = false) String cidade,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String cep,
            @RequestParam(required = false) Long clienteId,
            Pageable pageable) {
        return enderecoService.listar(rua, numero, complemento, bairro, cidade, estado, cep, clienteId, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(enderecoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody EnderecoRequestDTO enderecoDTO) {
        return ResponseEntity.ok(enderecoService.atualizar(id, enderecoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        enderecoService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
