package com.desafioMV.desafioMV.dto;

import java.time.LocalDateTime;

import com.desafioMV.desafioMV.entity.StatusNotificacao;

public record NotificacaoListarDTO(String titulo,String texto,LocalDateTime enviada,StatusNotificacao status) {
    
}
