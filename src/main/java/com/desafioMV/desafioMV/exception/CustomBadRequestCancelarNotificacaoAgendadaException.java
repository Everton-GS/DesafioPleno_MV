package com.desafioMV.desafioMV.exception;

public class CustomBadRequestCancelarNotificacaoAgendadaException extends RuntimeException{
            
    public CustomBadRequestCancelarNotificacaoAgendadaException(){
        super("A mensagem não pode ser alterada, pois já foi enviada.");
    }
}
