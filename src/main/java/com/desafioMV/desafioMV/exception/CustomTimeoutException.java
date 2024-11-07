package com.desafioMV.desafioMV.exception;

public class CustomTimeoutException extends RuntimeException {
    
    public CustomTimeoutException(){
        super("O tempo máximo para o processamento da requisição foi excedido.");
    }
}
