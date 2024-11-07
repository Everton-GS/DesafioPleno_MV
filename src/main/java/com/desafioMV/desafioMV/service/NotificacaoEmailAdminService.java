package com.desafioMV.desafioMV.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.desafioMV.desafioMV.dto.NotificacaoEmailAdminRegistrarDTO;
import com.desafioMV.desafioMV.entity.NotificacaoEmailAdmin;
import com.desafioMV.desafioMV.entity.StatusNotificacao;
import com.desafioMV.desafioMV.entity.Usuario;
import com.desafioMV.desafioMV.repository.NotificacaoEmailAdminRespository;

@Service
public class NotificacaoEmailAdminService {
    
    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String email;

    @Autowired
    NotificacaoEmailAdminRespository notificacaoEmailAdminRespository;


     public void emailMensagemEnviar(NotificacaoEmailAdmin notificacaoEmailAdmin){
        String assunto="Sistema MV - Comunicado";
        SimpleMailMessage simpleMailMessage= new SimpleMailMessage();
        simpleMailMessage.setFrom(email);
        simpleMailMessage.setTo(notificacaoEmailAdmin.getEmail());
        simpleMailMessage.setSubject(assunto);
        simpleMailMessage.setText(notificacaoEmailAdmin.getMensagem());
        javaMailSender.send(simpleMailMessage);
    }

     public void reegistrarEmailAdmin(NotificacaoEmailAdminRegistrarDTO emailAdminRegistrarDTO, Usuario usuario){
         NotificacaoEmailAdmin notificacaoEmailAdmin = new NotificacaoEmailAdmin(emailAdminRegistrarDTO.mensagem(),LocalDateTime.now(),emailAdminRegistrarDTO.enviar(),usuario.getEmail(), StatusNotificacao.AGENDADA);
          notificacaoEmailAdminRespository.save(notificacaoEmailAdmin);
     }
     public void cancelarEmailAdmin(NotificacaoEmailAdmin notificacaoEmailAdmin){
        notificacaoEmailAdmin.setStatus(StatusNotificacao.CANCELADO);
        notificacaoEmailAdminRespository.save(notificacaoEmailAdmin);
     }
     public void entregueEmailAdmin(NotificacaoEmailAdmin notificacaoEmailAdmin){
        notificacaoEmailAdmin.setStatus(StatusNotificacao.ENTREGUE);
        notificacaoEmailAdminRespository.save(notificacaoEmailAdmin);
     }

    
}
