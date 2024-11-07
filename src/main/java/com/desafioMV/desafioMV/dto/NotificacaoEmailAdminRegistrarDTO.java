package com.desafioMV.desafioMV.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record NotificacaoEmailAdminRegistrarDTO(String mensagem,@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "UTC")LocalDateTime enviar,Long idUsuario) {
    
}
