package com.desafioMV.desafioMV.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.desafioMV.desafioMV.entity.NotificacaoEmailAdmin;

public interface NotificacaoEmailAdminRespository extends JpaRepository<NotificacaoEmailAdmin,Long>{

    @Query(value = "SELECT * FROM db_notificacao_email_admin WHERE status = 'AGENDADA'", nativeQuery = true)
    List<NotificacaoEmailAdmin> findByNotificacaoEmailAdminAguardando();
    
}
