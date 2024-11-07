package com.desafioMV.desafioMV.exception;

public class CustomInternalServerError extends RuntimeException {
    
    public  CustomInternalServerError(){
        super("Erro interno no servidor");
    }
}
