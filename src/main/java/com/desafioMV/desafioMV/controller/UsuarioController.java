package com.desafioMV.desafioMV.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desafioMV.desafioMV.dto.UsuarioRegistroDTO;
import com.desafioMV.desafioMV.entity.Usuario;
import com.desafioMV.desafioMV.exception.CustomBadRequestRegistrarUsuario;
import com.desafioMV.desafioMV.exception.CustomInternalServerError;
import com.desafioMV.desafioMV.repository.UsuarioRepository;
import com.desafioMV.desafioMV.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/usuario")
public class UsuarioController {
    
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody UsuarioRegistroDTO usuarioDTO){
         try {
               Usuario usuario = usuarioRepository.findByEmail(usuarioDTO.email());
               if(usuario!=null){
                  throw new CustomBadRequestRegistrarUsuario();
               }
               usuarioService.registrarUsuario(usuarioDTO);
               return ResponseEntity.status(HttpStatus.CREATED).build();
         } catch (CustomBadRequestRegistrarUsuario custoBadRequestRegistrarUsuario) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(custoBadRequestRegistrarUsuario.getMessage());
         } catch(CustomInternalServerError customInternalServerError){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customInternalServerError.getMessage());
         }
    }


}
