package com.desafioMV.desafioMV.exception;

public class CustomNotificacaoNotFoundException extends RuntimeException {
    
    public CustomNotificacaoNotFoundException(){
        super("Não há notificações disponíveis no momento.");
    }
    
}
