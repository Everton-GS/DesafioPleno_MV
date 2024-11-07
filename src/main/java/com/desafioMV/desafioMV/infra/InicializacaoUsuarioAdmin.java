package com.desafioMV.desafioMV.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.desafioMV.desafioMV.entity.RoleUsuario;
import com.desafioMV.desafioMV.entity.Usuario;
import com.desafioMV.desafioMV.repository.UsuarioRepository;

@Component
public class InicializacaoUsuarioAdmin implements CommandLineRunner {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {

       String senhaCriptografada="$2a$10$duIivOBrOM5xAFcAITjNKuycNTLCZbYwdaOBgx.rGNgwatjFXghmy";
       Usuario usuario= usuarioRepository.findByEmail("AdministradorMV01@gmail.com");
       if(usuario==null){
        Usuario usuarioNovo = new Usuario("AdministradorMV01@gmail.com", senhaCriptografada,RoleUsuario.ADMINISTRADOR);
        usuarioRepository.save(usuarioNovo);
       }
    }
    
}
