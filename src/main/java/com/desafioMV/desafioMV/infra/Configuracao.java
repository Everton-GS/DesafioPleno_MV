package com.desafioMV.desafioMV.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class Configuracao {

    @Autowired
    FiltroSeguranca filtroSeguranca;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
            .requestMatchers(HttpMethod.POST,"/acessar").permitAll()
            .requestMatchers(HttpMethod.GET,"/actuator/health").permitAll()
            .requestMatchers(HttpMethod.GET,"/actuator/**").permitAll()
            .requestMatchers(HttpMethod.POST,"/notifications/registrar").hasRole("Usuario")
            .requestMatchers(HttpMethod.POST,"/notifications/listar").hasRole("Usuario")
            .requestMatchers(HttpMethod.PUT,"/notifications/{notificationId}/mark-as-read").hasRole("Usuario")
            .requestMatchers(HttpMethod.POST,"/usuario/registrar").hasRole("Administrador")
            .requestMatchers(HttpMethod.PUT,"/notificacao/admin/email/cancelar/{notificacaoAgendada}").hasRole("Administrador")
            .requestMatchers(HttpMethod.POST,"/notificacao/admin/email/registrar").hasRole("Administrador")
            .requestMatchers(HttpMethod.PUT,"/notificacao/admin/email/enviar").hasRole("Administrador")
            .anyRequest().authenticated())
            .addFilterBefore(filtroSeguranca,UsernamePasswordAuthenticationFilter.class)
            .httpBasic(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults());
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .components(new Components()
                .addSecuritySchemes("bearerAuth",
                    new SecurityScheme()
                        .name("Authorization")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"))
            )
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
