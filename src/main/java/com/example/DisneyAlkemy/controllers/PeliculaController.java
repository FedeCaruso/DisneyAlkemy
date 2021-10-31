/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.DisneyAlkemy.controllers;

import com.example.DisneyAlkemy.exceptions.WebException;
import com.example.DisneyAlkemy.models.PeliculaOSerie;
import com.example.DisneyAlkemy.services.PeliculaOSerieService;
import com.example.DisneyAlkemy.services.PersonajeService;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Fede
 */
@Controller
@RequestMapping("/movies")
public class PeliculaController {

    @Autowired
    private PeliculaOSerieService peliculaOSerieService;
    
    @Autowired
    private PersonajeService personajeService;

    @GetMapping("/list")
    public String listarPeliculas(Model model, @RequestParam(required = false) String q) {
        if (q != null) {
            try {
                model.addAttribute("peliculas", peliculaOSerieService.listAllByQ(q));
            } catch (WebException ex) {
                Logger.getLogger(PeliculaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                model.addAttribute("peliculas", peliculaOSerieService.findAll());
            } catch (WebException ex) {
                Logger.getLogger(PeliculaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "movie-list";
    }

    @GetMapping("/form")
    public String crearPelicula(Model model, @RequestParam(required = false) String id, @RequestParam(required = false) String action) {
        if (id != null) {
            try {
                Optional<PeliculaOSerie> optional = peliculaOSerieService.findById(id);
                if (optional.isPresent()) {
                    model.addAttribute("personajes", personajeService.findAll());
                    model.addAttribute("pelicula", optional.get());
                    model.addAttribute("action", action);
                } else {
                    return "movie-list";
                }
            } catch (WebException ex) {
                Logger.getLogger(PeliculaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            model.addAttribute("pelicula", new PeliculaOSerie());
            model.addAttribute("action", action);
        }
        return "movie-form";
    }

    @GetMapping("/details")
    public String DetallesPelicula(Model model, @RequestParam(required = false) String id, @RequestParam(required = false) String action) {
        if (id != null) {
            try {
                Optional<PeliculaOSerie> optional = peliculaOSerieService.findById(id);
                if (optional.isPresent()) {
                    model.addAttribute("pelicula", optional.get());

                } else {
                    return "redirect:/";
                }
            } catch (WebException ex) {
                Logger.getLogger(PeliculaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            model.addAttribute("personaje", new PeliculaOSerie());

        }
        return "movie-details";
    }

    @GetMapping("/delete")
    public String eliminarPelicula(@RequestParam(required = true) String id) {
        try {
            peliculaOSerieService.deleteById(id);
            return "redirect:/movies/list";
        } catch (WebException ex) {
            Logger.getLogger(PeliculaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/movies/list";
    }

    @PostMapping("/save")
    public String guardarPelicula(Model model, @RequestParam(required = false) MultipartFile file, RedirectAttributes redirectAttributes,
            @ModelAttribute PeliculaOSerie pelicula, @RequestParam(required = true) String action, @RequestParam(required = true) String id) {
        try {
            if (action.equals("edit")) {
                PeliculaOSerie peliculaBuscada = peliculaOSerieService.findById(id).get();
                peliculaOSerieService.modificarPelicula(file, peliculaBuscada, pelicula);
                redirectAttributes.addFlashAttribute("success", "Pelicula modificada con éxito.");
                return "redirect:/movies/list";
            } else {
                peliculaOSerieService.save(pelicula, file);
                redirectAttributes.addFlashAttribute("success", "Pelicula guardada con éxito.");
            }

        } catch (WebException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("pelicula", pelicula);
            return "movie-form";
        }
        return "redirect:/movies/list";
    }
}
