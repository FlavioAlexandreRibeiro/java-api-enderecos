package com.API_de_Enderecos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> tratarClienteNaoEncontrado(
            ClienteNaoEncontradoException exception) {

        ErroResponse erro = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Cliente não encontrado",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(erro);
    }


    @ExceptionHandler(EnderecoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> tratarEnderecoNaoEncontrado(
            EnderecoNaoEncontradoException exception) {

        ErroResponse erro = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Endereço não encontrado",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(erro);
    }


    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<ErroResponse> tratarEmailJaCadastrado(
            EmailJaCadastradoException exception) {

        ErroResponse erro = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Email já cadastrado",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(erro);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> tratarErrosDeValidacao(
            MethodArgumentNotValidException exception) {

        String mensagem = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Dados inválidos");

        ErroResponse erro = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                mensagem
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erro);
    }
}
