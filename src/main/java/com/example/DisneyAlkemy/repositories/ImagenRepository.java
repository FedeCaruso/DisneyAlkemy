/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.DisneyAlkemy.repositories;

import com.example.DisneyAlkemy.models.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Fede
 */
public interface ImagenRepository extends JpaRepository<Imagen, String> {
    
}
