package com.desafioMV.desafioMV.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.desafioMV.desafioMV.dto.UsuarioRegistroDTO;
import com.desafioMV.desafioMV.entity.RoleUsuario;
import com.desafioMV.desafioMV.entity.Usuario;
import com.desafioMV.desafioMV.repository.UsuarioRepository;

@Service
public class UsuarioService {
    

    @Autowired
    UsuarioRepository usuarioRepository;

    public void registrarUsuario(UsuarioRegistroDTO usuarioDTO){
        String senhaEncrypter= new BCryptPasswordEncoder().encode(usuarioDTO.senha());
        Usuario usuario = new Usuario(usuarioDTO.email(),senhaEncrypter, RoleUsuario.USUARIO);
        usuarioRepository.save(usuario);
       
    }
}
