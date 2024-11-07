package com.desafioMV.desafioMV.exception;

public class CustomNotificacaoEmailAdminNotFoundException extends RuntimeException {
    
    public CustomNotificacaoEmailAdminNotFoundException(){
        super("Notificação não encontrada no banco de dados.");
    }
}
