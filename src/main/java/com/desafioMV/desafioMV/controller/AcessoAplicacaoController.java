package com.desafioMV.desafioMV.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desafioMV.desafioMV.dto.AcessarAplicacaoDTO;
import com.desafioMV.desafioMV.entity.Usuario;
import com.desafioMV.desafioMV.exception.CustomAuthenticationException;
import com.desafioMV.desafioMV.exception.CustomInternalServerError;
import com.desafioMV.desafioMV.infra.TokenService;
import com.desafioMV.desafioMV.repository.UsuarioRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/")
public class AcessoAplicacaoController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenService tokenService;
    
    @PostMapping("acessar")
    public ResponseEntity<String> acessarAplicacao(@Valid @RequestBody AcessarAplicacaoDTO acessarAplicacaoDTO){
            try {
                UserDetails usuario = usuarioRepository.findByEmail(acessarAplicacaoDTO.email());
                 if(usuario==null || !passwordEncoder.matches(acessarAplicacaoDTO.senha(),usuario.getPassword())){
                    throw new CustomAuthenticationException();
                 }
                 if(usuario!=null && passwordEncoder.matches(acessarAplicacaoDTO.senha(),usuario.getPassword())){
                    Authentication authentication = new UsernamePasswordAuthenticationToken(usuario,null, usuario.getAuthorities());
                    String tokenGerado = tokenService.gerarTokenUsuario((Usuario) authentication.getPrincipal());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return ResponseEntity.status(HttpStatus.OK).body(tokenGerado);
                 }
               return ResponseEntity.badRequest().build();
            } catch (CustomAuthenticationException customAuthenticationException) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(customAuthenticationException.getMessage());
            } catch(CustomInternalServerError customInternalServerError){
               throw new  CustomInternalServerError();
            }
    }
}
