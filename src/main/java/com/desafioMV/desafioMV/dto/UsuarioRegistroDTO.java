package com.desafioMV.desafioMV.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRegistroDTO(@Email @NotBlank String email,@NotBlank String senha) {
    
}
