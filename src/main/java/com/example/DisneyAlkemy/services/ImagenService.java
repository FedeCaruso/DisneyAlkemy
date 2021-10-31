/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.DisneyAlkemy.services;

import com.example.DisneyAlkemy.exceptions.WebException;
import com.example.DisneyAlkemy.models.Imagen;
import com.example.DisneyAlkemy.repositories.ImagenRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Fede
 */
@Service
public class ImagenService {
    
    @Autowired
    private ImagenRepository imagenRepository;

    @Transactional
    public Imagen save(MultipartFile file) throws WebException {
        if (file != null) {
            try {
               Imagen imagen = new Imagen();
                imagen.setMime(file.getContentType());
                imagen.setNombre(file.getName());
                imagen.setContenido(file.getBytes());
                return imagenRepository.save(imagen);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    @Transactional
    public Imagen actualizar(String idFoto, MultipartFile file) {
        if (file != null) {
            try {
                Imagen imagen = new Imagen();
                
                if(idFoto != null) {
                    Optional<Imagen> respuesta = imagenRepository.findById(idFoto);
                    if(respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }
                imagen.setMime(file.getContentType());
                imagen.setNombre(file.getName());
                imagen.setContenido(file.getBytes());
                return imagenRepository.save(imagen);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
    
     @Transactional
    public void delete(Imagen imagen) {
        imagenRepository.delete(imagen);
    }
}
