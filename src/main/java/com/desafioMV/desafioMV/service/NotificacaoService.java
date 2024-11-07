package com.desafioMV.desafioMV.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.desafioMV.desafioMV.dto.NotificacaoDTO;
import com.desafioMV.desafioMV.entity.Notificacao;
import com.desafioMV.desafioMV.entity.StatusNotificacao;
import com.desafioMV.desafioMV.entity.Usuario;
import com.desafioMV.desafioMV.repository.NotificacaoRepository;
import com.desafioMV.desafioMV.repository.UsuarioRepository;

@Service
public class NotificacaoService {
    

    @Autowired
    NotificacaoRepository notificacaoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public void registrarNotificacao(NotificacaoDTO notificacaoDTO, Usuario remetente,Usuario destinatario){
        Notificacao notificacao = new Notificacao(notificacaoDTO.titulo(),notificacaoDTO.notificacao(),notificacaoDTO.tipoNotificacao(), destinatario, remetente, StatusNotificacao.ENTREGUE);
        notificacaoRepository.save(notificacao);
    }

    public List<Notificacao> listarNotificacao(Long id){
        List<Notificacao> notificacao= notificacaoRepository.findByIDUser(id);
        return notificacao;
    }

    public void atulizarStatus(Notificacao notificacao){
        notificacao.setStatusNotificacao(StatusNotificacao.LIDA);
    }
    
}
