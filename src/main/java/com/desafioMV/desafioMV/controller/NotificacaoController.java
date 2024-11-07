package com.desafioMV.desafioMV.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desafioMV.desafioMV.dto.NotificacaoDTO;
import com.desafioMV.desafioMV.dto.NotificacaoListarDTO;
import com.desafioMV.desafioMV.entity.Notificacao;
import com.desafioMV.desafioMV.entity.Usuario;
import com.desafioMV.desafioMV.exception.CustomBadRequestException;
import com.desafioMV.desafioMV.exception.CustomInternalServerError;
import com.desafioMV.desafioMV.exception.CustomNotificacaoNotFoundException;
import com.desafioMV.desafioMV.exception.CustomUnauthorizedActionException;
import com.desafioMV.desafioMV.exception.CustomUserNotFoundException;
import com.desafioMV.desafioMV.repository.NotificacaoRepository;
import com.desafioMV.desafioMV.repository.UsuarioRepository;
import com.desafioMV.desafioMV.service.NotificacaoService;

import jakarta.validation.Valid;

import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping(path = "/")
public class NotificacaoController {

    @Autowired
    NotificacaoService notificacaoService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    NotificacaoRepository notificacaoRepository;

    @Transactional
    @PostMapping("notifications/registrar")
    public ResponseEntity<?> registrarNotificacao(@Valid @RequestBody NotificacaoDTO notificacaoDTO) {
            try {
                Usuario usuarioRemetente = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Optional<Usuario> usuarioDestinatario = usuarioRepository.findById(notificacaoDTO.idUsuaruio());
                if (!usuarioDestinatario.isPresent()) {
                   throw new CustomUserNotFoundException();
                }
                if(usuarioRemetente.getId()==notificacaoDTO.idUsuaruio()){
                    throw new CustomBadRequestException();
                }
                  notificacaoService.registrarNotificacao(notificacaoDTO,usuarioRemetente,usuarioDestinatario.get());
                  return ResponseEntity.status(HttpStatus.CREATED).build();
              }catch(CustomUserNotFoundException customUserNotFoundException){
                  return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customUserNotFoundException.getMessage());
              }catch(CustomBadRequestException badRequestException){
                  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestException.getMessage());
              }
            }

    @Transactional(readOnly = true)
    @GetMapping("notifications/listar")
    public ResponseEntity<?> listarNotificacao(){
         try {
               Usuario usuario= (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
               List<Notificacao> notificacaoRecebida;
               notificacaoRecebida = notificacaoRepository.findByDestinatario(usuario.getId());
               if(notificacaoRecebida.isEmpty()){
                throw new CustomNotificacaoNotFoundException();
                }
               List<NotificacaoListarDTO> listarFiltrada; 
               listarFiltrada= notificacaoRecebida.stream().map(listaNova->new NotificacaoListarDTO(listaNova.getTitulo(),
                                                                                                    listaNova.getCorpoNotificacao(),
                                                                                                    listaNova.getCriacaoNotificacao(), 
                                                                                                    listaNova.getStatusNotificacao())).collect(Collectors.toList());
               return ResponseEntity.ok(listarFiltrada);
         } catch (CustomInternalServerError customInternalServerError) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customInternalServerError.getMessage());
         }catch(CustomNotificacaoNotFoundException customNotificacaoNotFoundException){
            return ResponseEntity.status(HttpStatus.OK).body(customNotificacaoNotFoundException.getMessage());
         }            
    }

    @Transactional
    @PutMapping("notifications/{notificationId}/mark-as-read")
    public ResponseEntity<?> atualizarStatusNotificacao(@PathVariable Long notificationId){
        try {
            Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<Notificacao> notificacao=notificacaoRepository.findById(notificationId);
            if(!notificacao.isPresent()){
            throw new CustomNotificacaoNotFoundException();
            }
            if(!notificacao.get().getDestinatario().getId().equals(usuario.getId())){
            throw new CustomUnauthorizedActionException();
            }
            notificacaoService.atulizarStatus(notificacao.get());
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (CustomNotificacaoNotFoundException customNotificacaoNotFoundException) {
            return ResponseEntity.status(HttpStatus.OK).body(customNotificacaoNotFoundException.getMessage());
        }catch(CustomUnauthorizedActionException customUnauthorizedActionException){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(customUnauthorizedActionException.getMessage());
        } catch (CustomInternalServerError customInternalServerError) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customInternalServerError.getMessage());
    }
}
}