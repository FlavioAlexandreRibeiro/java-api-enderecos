package com.API_de_Enderecos.exception;

import java.time.LocalDateTime;

public record ErroResponse (
        LocalDateTime timestamp,
        int status,
        String erro,
        String mensagem
){
}
