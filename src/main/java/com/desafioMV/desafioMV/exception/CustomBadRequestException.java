package com.desafioMV.desafioMV.exception;

public class CustomBadRequestException extends RuntimeException {

    public CustomBadRequestException(){
        super("O parâmetro id não pode ser o mesmo que o de sua conta.");
    }
}
