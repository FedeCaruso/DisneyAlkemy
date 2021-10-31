package com.example.DisneyAlkemy.controllers;

import com.example.DisneyAlkemy.exceptions.WebException;
import com.example.DisneyAlkemy.models.PeliculaOSerie;
import com.example.DisneyAlkemy.models.Personaje;
import com.example.DisneyAlkemy.services.PeliculaOSerieService;
import com.example.DisneyAlkemy.services.PersonajeService;
import java.util.Optional;
import java.util.logging.Level;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Fede
 */
@Controller
@RequestMapping("/imagen")
public class ImagenController {

    @Autowired
    private PersonajeService personajeService;

    @Autowired
    private PeliculaOSerieService peliculaOSerieService;

    @GetMapping("/personaje")
    public ResponseEntity<byte[]> imagenPersonaje(@RequestParam(required = true) String id) {

        try {
            Personaje personaje = personajeService.buscarPorId(id);

            if (personaje.getImagen() == null) {
                throw new WebException("El personaje no tiene una imagen asignada.");
            }

            byte[] imagen = personaje.getImagen().getContenido();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(imagen, headers, HttpStatus.OK);

        } catch (WebException ex) {
            java.util.logging.Logger.getLogger(ImagenController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/peliculaOSerie")
    public ResponseEntity<byte[]> imagenPelicula(@RequestParam(required = true) String id) {

        try {
            PeliculaOSerie pelicula = peliculaOSerieService.buscarPorId(id);

            if (pelicula.getImagen() == null) {
                throw new WebException("La pelicula no tiene una imagen asignada.");
            }

            byte[] imagen = pelicula.getImagen().getContenido();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(imagen, headers, HttpStatus.OK);

        } catch (WebException ex) {
            java.util.logging.Logger.getLogger(ImagenController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
