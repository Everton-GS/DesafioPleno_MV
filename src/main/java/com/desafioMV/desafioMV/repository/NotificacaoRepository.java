package com.desafioMV.desafioMV.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.desafioMV.desafioMV.entity.Notificacao;

public interface NotificacaoRepository  extends JpaRepository<Notificacao,Long>{
    
    @Query(value = "Select * from db_notificacao where id=:id_cliente",nativeQuery = true)
    List<Notificacao>findByIDUser(@Param("id")Long id);

    @Query(value = "SELECT * FROM db_notificacao WHERE id_destinatario = :id_destinatario", nativeQuery = true)
    List<Notificacao> findByDestinatario(@Param("id_destinatario") Long idDestinatario);

}
