package com.desafioMV.desafioMV.infra;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.desafioMV.desafioMV.entity.Usuario;

@Service
public class TokenService {

    @Value("${spring.secret.key}")
    private String secret;
    
    public String gerarTokenUsuario(Usuario usuario){
        try {
             Algorithm algorithm = Algorithm.HMAC512(secret);
             String token = JWT.create().withIssuer("MV").withSubject(usuario.getEmail()).withExpiresAt(tempoToken()).sign(algorithm);
             return token;
        } catch (JWTCreationException jwtCreationException) {
            throw new RuntimeException("Erro na criação do token JWT", jwtCreationException);
        }
    }

    public String validarToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            return JWT.require(algorithm).withIssuer("MV").build().verify(token).getSubject();
        } catch (JWTVerificationException jwtVerificationException) {
            return "";
        }
    }

    private Instant tempoToken(){
        return LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.of("-03:00"));
    }

    
}
