package com.desafioMV.desafioMV.infra;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.desafioMV.desafioMV.repository.UsuarioRepository;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;

@Component
public class FiltroSeguranca extends OncePerRequestFilter {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TokenService tokenService;
    
    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,@NonNull HttpServletResponse response, @NonNull FilterChain filterChain)throws ServletException, IOException {

        String token= this.recoverToken(request);
        if(token!= null){
            String email = tokenService.validarToken(token);
            UserDetails user;
            user= usuarioRepository.findByEmail(email);
            if(user!=null){
                var authentication = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
