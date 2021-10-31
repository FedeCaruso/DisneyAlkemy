/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.DisneyAlkemy.repositories;

import com.example.DisneyAlkemy.models.Personaje;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Fede
 */
@Controller
public interface PersonajeRepository extends JpaRepository<Personaje, String>{
    
    @Query("SELECT c FROM Personaje c WHERE c.nombre LIKE :q or c.edad LIKE :q"
            + " or c.peso LIKE :q")
    List<Personaje> findAllByQ(@Param("q") String q);
    
    @Query("SELECT c FROM Personaje c WHERE c.id like :id")
    public Personaje buscarPorId(@Param("id") String id);
}
