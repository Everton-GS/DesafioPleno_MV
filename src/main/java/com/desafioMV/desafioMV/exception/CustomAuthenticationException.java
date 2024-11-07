package com.desafioMV.desafioMV.exception;

import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {
    
    public CustomAuthenticationException() {
        super("Credenciais incorretas.");
    }
}