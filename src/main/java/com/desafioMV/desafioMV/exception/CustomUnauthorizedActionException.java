package com.desafioMV.desafioMV.exception;

public class CustomUnauthorizedActionException extends RuntimeException {
    
    public CustomUnauthorizedActionException(){
        super("Você não tem permissão para realizar essa ação");
    }
}
