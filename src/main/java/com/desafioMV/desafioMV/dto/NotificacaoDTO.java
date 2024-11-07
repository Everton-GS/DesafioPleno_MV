package com.desafioMV.desafioMV.dto;

import com.desafioMV.desafioMV.entity.TipoNotificacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record NotificacaoDTO(@NotBlank String titulo,@NotBlank String notificacao,@NotNull TipoNotificacao tipoNotificacao,@NotNull Long idUsuaruio) {
    
}
