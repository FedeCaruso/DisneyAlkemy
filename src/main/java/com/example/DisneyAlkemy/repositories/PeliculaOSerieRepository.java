/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.DisneyAlkemy.repositories;

import com.example.DisneyAlkemy.models.PeliculaOSerie;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Fede
 */
public interface PeliculaOSerieRepository extends JpaRepository<PeliculaOSerie, String> {
    @Query("SELECT c FROM PeliculaOSerie c WHERE c.titulo LIKE :q or c.calificacion LIKE :q")
    List<PeliculaOSerie> findAllByQ(@Param("q") String q);
    
    @Query("SELECT c FROM PeliculaOSerie c WHERE c.id like :id")
    public PeliculaOSerie buscarPorId(@Param("id") String id);
    
}
