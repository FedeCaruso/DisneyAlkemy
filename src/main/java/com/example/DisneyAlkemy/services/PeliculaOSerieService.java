/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.DisneyAlkemy.services;

import com.example.DisneyAlkemy.exceptions.WebException;
import com.example.DisneyAlkemy.models.Imagen;
import com.example.DisneyAlkemy.models.PeliculaOSerie;
import com.example.DisneyAlkemy.repositories.PeliculaOSerieRepository;
import java.util.List;
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
public class PeliculaOSerieService {

    @Autowired
    private PeliculaOSerieRepository peliculaRepository;

    @Autowired
    private ImagenService imagenService;

    @Transactional
    public PeliculaOSerie save(PeliculaOSerie pelicula, MultipartFile file) throws WebException {
        validarPelicula(pelicula);

        Imagen imagen = imagenService.save(file);
        pelicula.setImagen(imagen);
        return peliculaRepository.save(pelicula);
    }

    private void validarPelicula(PeliculaOSerie pelicula) throws WebException {

        if (pelicula.getTitulo() == null | pelicula.getTitulo().isEmpty()) {
            throw new WebException("La pelicula debería tener un nombre.");
        } else {
            pelicula.setTitulo(pelicula.getTitulo());
        }
        if (pelicula.getFechaDeCreacion() == null) {
            throw new WebException("La pelicula debe tener su fecha de creación.");
        } else {
            pelicula.setFechaDeCreacion(pelicula.getFechaDeCreacion());
        }
        if (pelicula.getPersonajes()== null) {
            pelicula.setPersonajes(null);
//            throw new WebException("Por favor, dime algun personaje de la pelicula.");
        } else {
            pelicula.setCalificacion(pelicula.getCalificacion());
        }
        if (pelicula.getCalificacion()== null) { 
            throw new WebException("Por favor indica el peso del pelicula.");
        } else {
            pelicula.setCalificacion(pelicula.getCalificacion());
        }
    }
    
    public List<PeliculaOSerie> findAll() throws WebException {
        return peliculaRepository.findAll();
    }
    
    public Optional<PeliculaOSerie> findById(String id) throws WebException {
        return peliculaRepository.findById(id);
    }
    
     public PeliculaOSerie buscarPorId(String id) throws WebException {
        return peliculaRepository.buscarPorId(id);
    }
    
    public List<PeliculaOSerie> listAllByQ(String q) throws WebException {
        return peliculaRepository.findAllByQ("%" + q + "%");
    }
    
    @Transactional
    public void deleteById(String id) throws WebException {
        Optional<PeliculaOSerie> optional = peliculaRepository.findById(id);
        if (optional.isPresent()) {
            imagenService.delete(optional.get().getImagen());
            peliculaRepository.delete(optional.get());
        }
    }
    
    @Transactional
    public void modificarPelicula(MultipartFile file, PeliculaOSerie peliculaBuscada, PeliculaOSerie pelicula) throws WebException {

        pelicula.setId(peliculaBuscada.getId());
        pelicula.setTitulo(pelicula.getTitulo());
        pelicula.setCalificacion(pelicula.getCalificacion());
        pelicula.setFechaDeCreacion(pelicula.getFechaDeCreacion());
        pelicula.setPersonajes(pelicula.getPersonajes());
        
        String idImagen = null;
        if (pelicula.getImagen() != null) {
            idImagen = pelicula.getImagen().getId();
        }
        Imagen imagen = imagenService.actualizar(idImagen, file);
        pelicula.setImagen(imagen);
        
        peliculaRepository.save(pelicula);
        
    }
}
