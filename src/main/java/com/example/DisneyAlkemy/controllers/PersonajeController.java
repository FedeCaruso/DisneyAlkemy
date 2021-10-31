/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.DisneyAlkemy.controllers;

import com.example.DisneyAlkemy.exceptions.WebException;
import com.example.DisneyAlkemy.models.Personaje;
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
@RequestMapping("/characters")
public class PersonajeController {

    @Autowired
    private PersonajeService personajeService;

    @GetMapping("/list")
    public String listarPersonajes(Model model, @RequestParam(required = false) String q) {
        if (q != null) {
            try {
                model.addAttribute("personajes", personajeService.listAllByQ(q));
            } catch (WebException ex) {
                Logger.getLogger(PersonajeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                model.addAttribute("personajes", personajeService.findAll());
            } catch (WebException ex) {
                Logger.getLogger(PersonajeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "character-list";
    }

    @GetMapping("/form")
    public String crearPersonaje(Model model, @RequestParam(required = false) String id, @RequestParam(required = false) String action) {
        if (id != null) {
            try {
                Optional<Personaje> optional = personajeService.findById(id);
                if (optional.isPresent()) {
                    model.addAttribute("personaje", optional.get());
                    model.addAttribute("action", action);
                } else {
                    return "character-list";
                }
            } catch (WebException ex) {
                Logger.getLogger(PersonajeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            model.addAttribute("personaje", new Personaje());
            model.addAttribute("action", action);
        }
        return "character-form";
    }

    @GetMapping("/details")
    public String DetallesPersonaje(Model model, @RequestParam(required = false) String id, @RequestParam(required = false) String action) {
        if (id != null) {
            try {
                Optional<Personaje> optional = personajeService.findById(id);
                if (optional.isPresent()) {
                    model.addAttribute("personaje", optional.get());

                } else {
                    return "redirect:/";
                }
            } catch (WebException ex) {
                Logger.getLogger(PersonajeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            model.addAttribute("personaje", new Personaje());

        }
        return "character-details";
    }

    @GetMapping("/delete")
    public String eliminarPersonaje(@RequestParam(required = true) String id) {
        try {
            personajeService.deleteById(id);
            return "redirect:/characters/list";
        } catch (WebException ex) {
            Logger.getLogger(PersonajeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/characters/list";
    }

    @PostMapping("/save")
    public String guardarPersonaje(Model model, @RequestParam(required = false) MultipartFile file, RedirectAttributes redirectAttributes,
            @ModelAttribute Personaje personaje, @RequestParam(required = true) String action, @RequestParam(required = true) String id) {
        try {
            if (action.equals("edit")) {
               Personaje personajeBuscado = personajeService.findById(id).get();
                personajeService.modificarPersonaje(file, personajeBuscado, personaje);
                redirectAttributes.addFlashAttribute("success", "Personaje modificado con éxito.");
                return "redirect:/characters/list";
            } else {
                personajeService.save(personaje, file);
                redirectAttributes.addFlashAttribute("success", "Personaje guardado con éxito.");
            }

        } catch (WebException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("personaje", personaje);
            return "character-form";
        }
        return "redirect:/characters/list";
    }
}
