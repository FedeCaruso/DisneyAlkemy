/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.DisneyAlkemy.repositories;
import com.example.DisneyAlkemy.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Fede
 */
public interface UsuarioRepository extends JpaRepository<Usuario, String>{
    
    @Query("SELECT u FROM Usuario u WHERE u.username = :username")
    Usuario findByUsername(@Param("username") String username);
    
}
