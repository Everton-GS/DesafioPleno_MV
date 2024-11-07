package com.desafioMV.desafioMV.entity;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@Entity
@Table(name = "db_notificacao")
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O titulo não pode estar em branco")
    @Column(name = "titulo")
    private String titulo;

    @NotBlank(message = "O texto não pode estar em branco")
    @Column(name = "texto")
    private String corpoNotificacao;

    @NotNull(message = "O tipo de Notificação não pode ser nula")
    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    TipoNotificacao tipoNotificacao;

    @ManyToOne
    @JoinColumn(name = "id_destinatario")
    Usuario destinatario;

    @ManyToOne
    @JoinColumn(name = "id_remetente")
    Usuario remetente;

    @NotNull(message = "O tipo de status da notificação não pode ser nulo")
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    StatusNotificacao statusNotificacao;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone = "UTC")
    @Column(name = "envio")
    private LocalDateTime criacaoNotificacao;

    public Notificacao(String titulo,String corpoNotificacao,TipoNotificacao tipoNotificacao,Usuario destinatario,Usuario remetente,StatusNotificacao statusNotificacao) {
    this.titulo = titulo;
    this.corpoNotificacao = corpoNotificacao;
    this.tipoNotificacao = tipoNotificacao;
    this.destinatario = destinatario;
    this.remetente = remetente;
    this.statusNotificacao = statusNotificacao;
    this.criacaoNotificacao=LocalDateTime.now();
}

    
    
}
