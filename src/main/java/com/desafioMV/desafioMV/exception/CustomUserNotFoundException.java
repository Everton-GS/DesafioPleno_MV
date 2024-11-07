package com.desafioMV.desafioMV.exception;

public class  CustomUserNotFoundException  extends RuntimeException{
    
    public  CustomUserNotFoundException(){
        super("Usuario não encontrado no banco de dados");
    }
}
