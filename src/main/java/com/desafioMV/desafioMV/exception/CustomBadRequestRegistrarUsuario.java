package com.desafioMV.desafioMV.exception;

public class CustomBadRequestRegistrarUsuario extends RuntimeException {
    

    public CustomBadRequestRegistrarUsuario(){
         super("Utilize outro e-mail, pois o informado já está em utilização.");
    }
}
