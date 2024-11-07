package com.desafioMV.desafioMV.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.desafioMV.desafioMV.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Long>{
    
    @Query(value = "Select * from db_usuario where email=:email",nativeQuery = true)
    Usuario findByEmail(@Param("email")String email);
}
