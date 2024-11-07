package com.desafioMV.desafioMV.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desafioMV.desafioMV.dto.NotificacaoEmailAdminRegistrarDTO;
import com.desafioMV.desafioMV.entity.NotificacaoEmailAdmin;
import com.desafioMV.desafioMV.entity.StatusNotificacao;
import com.desafioMV.desafioMV.entity.Usuario;
import com.desafioMV.desafioMV.exception.CustomBadRequestCancelarNotificacaoAgendadaException;
import com.desafioMV.desafioMV.exception.CustomInternalServerError;
import com.desafioMV.desafioMV.exception.CustomNotificacaoEmailAdminNotFoundException;
import com.desafioMV.desafioMV.exception.CustomTimeoutException;
import com.desafioMV.desafioMV.exception.CustomUserNotFoundException;
import com.desafioMV.desafioMV.repository.NotificacaoEmailAdminRespository;
import com.desafioMV.desafioMV.repository.UsuarioRepository;
import com.desafioMV.desafioMV.service.NotificacaoEmailAdminService;


@RestController
@RequestMapping("/notificacao/admin/email")
public class NotificacaoEmailAdminController {
    
    @Autowired
    NotificacaoEmailAdminRespository notificacaoEmailAdminRespository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    NotificacaoEmailAdminService notificacaoEmailAdminService;

    @Transactional
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarNotificacaoEmailAdmin(@RequestBody NotificacaoEmailAdminRegistrarDTO emailAdminRegistrarDTO){
        try {
            Optional<Usuario> usuario = usuarioRepository.findById(emailAdminRegistrarDTO.idUsuario());
            if(!usuario.isPresent()){
               throw new CustomUserNotFoundException();
            }
            notificacaoEmailAdminService.reegistrarEmailAdmin(emailAdminRegistrarDTO,usuario.get());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (CustomUserNotFoundException customUserNotFoundException) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customUserNotFoundException.getMessage());
        }
    }
    @Transactional
    @PutMapping("/enviar")
    public ResponseEntity<?> enviarNotificacaoAgendada(){
        try {
            List<NotificacaoEmailAdmin> notificacaoAgendada= notificacaoEmailAdminRespository.findByNotificacaoEmailAdminAguardando();
            if(!notificacaoAgendada.isEmpty()){
                for(NotificacaoEmailAdmin notificacaoEmailAdmin:notificacaoAgendada){
                    if(LocalDateTime.now().isAfter(notificacaoEmailAdmin.getEnvio())){
                        notificacaoEmailAdminService.emailMensagemEnviar(notificacaoEmailAdmin);
                        notificacaoEmailAdminService.entregueEmailAdmin(notificacaoEmailAdmin);
                    }
                }
                return ResponseEntity.status(HttpStatus.ACCEPTED).build();
            }
        } catch (CustomTimeoutException customTimeoutException) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(customTimeoutException.getMessage());
           } 
            catch(CustomInternalServerError customInternalServerError){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customInternalServerError.getMessage());
        }
            return ResponseEntity.ok().build();
    }

    @Transactional
    @PutMapping("/cancelar/{notificacaoAgendada}")
    public ResponseEntity<?> cancelarNotificacaoAgendada(@PathVariable Long notificacaoAgendada){
        try{
            Optional<NotificacaoEmailAdmin> notificacao= notificacaoEmailAdminRespository.findById(notificacaoAgendada);
            if(!notificacao.isPresent()){
                throw new CustomNotificacaoEmailAdminNotFoundException();
            }
            if(notificacao.get().getStatus()==StatusNotificacao.ENTREGUE){
                throw new CustomBadRequestCancelarNotificacaoAgendadaException();
            }
             notificacaoEmailAdminService.cancelarEmailAdmin(notificacao.get());
             return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }catch(CustomNotificacaoEmailAdminNotFoundException customNotificacaoEmailAdminNotFoundException){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customNotificacaoEmailAdminNotFoundException.getMessage());
        }catch(CustomInternalServerError customInternalServerError){
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customInternalServerError.getMessage());
        }catch(CustomBadRequestCancelarNotificacaoAgendadaException customBadRequestCancelarNotificacaoAgendadaException){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customBadRequestCancelarNotificacaoAgendadaException.getMessage());
        }
    }
    
}
