package com.desafioMV.desafioMV.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "db_usuario")
public class Usuario implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long id;
    
    @NotBlank(message = "O campo email não pode estar em branco")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "O campo senha não pode estar em branco")
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_acesso")
    private RoleUsuario roleUsuario;

    @OneToMany(mappedBy = "destinatario")
    private List<Notificacao> notificacoes;
    
    public Usuario(@NotBlank(message = "O campo email não pode estar em branco") String email,
            @NotBlank(message = "O campo senha não pode estar em branco") String senha, RoleUsuario roleUsuario) {
        this.email = email;
        this.senha = senha;
        this.roleUsuario = roleUsuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roleUsuario.getTipo()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
       return email;
    }
}
