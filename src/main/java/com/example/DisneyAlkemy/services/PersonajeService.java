/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.DisneyAlkemy.services;

import com.example.DisneyAlkemy.exceptions.WebException;
import com.example.DisneyAlkemy.models.Imagen;
import com.example.DisneyAlkemy.models.Personaje;
import com.example.DisneyAlkemy.repositories.PersonajeRepository;
import java.util.Date;
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
public class PersonajeService {
    
    @Autowired
    private PersonajeRepository personajeRepository;
    
    @Autowired
    private ImagenService imagenService;
    
    @Transactional
    public Personaje save(Personaje personaje, MultipartFile file) throws WebException {
        validarPersonaje(personaje);
        
        Imagen imagen = imagenService.save(file);
        personaje.setImagen(imagen);
        return personajeRepository.save(personaje);
    }
    
    private void validarPersonaje(Personaje personaje) throws WebException {
        
        if (personaje.getNombre() == null | personaje.getNombre().isEmpty()) {
            throw new WebException("El personaje debería tener un nombre.");
        } else {
            personaje.setNombre(personaje.getNombre());
        }
        if (personaje.getEdad() == null) {
            throw new WebException("El personaje debería tener una edad.");
        } else {
            personaje.setEdad(personaje.getEdad());
        }
        if (personaje.getHistoria() == null | personaje.getHistoria().isEmpty()) {
            personaje.setHistoria(null);
//            throw new WebException("Por favor, agrega una breve historia al personaje.");
        } else {
            personaje.setHistoria(personaje.getHistoria());
        }
        if (personaje.getPeso() == null) {
            throw new WebException("Por favor indica el peso del personaje.");
        } else {
            personaje.setPeso(personaje.getPeso());
        }
    }
    
    public List<Personaje> findAll() throws WebException {
        return personajeRepository.findAll();
    }
    
    public Optional<Personaje> findById(String id) throws WebException {
        return personajeRepository.findById(id);
    }
    
    public Personaje buscarPorId(String id) throws WebException {
        return personajeRepository.buscarPorId(id);
    }
    
    public List<Personaje> listAllByQ(String q) throws WebException {
        return personajeRepository.findAllByQ("%" + q + "%");
    }
    
    @Transactional
    public void deleteById(String id) throws WebException {
        Optional<Personaje> optional = personajeRepository.findById(id);
        if (optional.isPresent()) {
            imagenService.delete(optional.get().getImagen());
            personajeRepository.delete(optional.get());
        }
    }
    
    @Transactional
    public void modificarPersonaje(MultipartFile file, Personaje personajeBuscado, Personaje personaje) throws WebException {

        personaje.setId(personajeBuscado.getId());
        personaje.setNombre(personaje.getNombre());
        personaje.setEdad(personaje.getEdad());
        personaje.setHistoria(personaje.getHistoria());
        personaje.setPeso(personaje.getPeso());
        
        String idImagen = null;
        if (personaje.getImagen() != null) {
            idImagen = personaje.getImagen().getId();
        }
        Imagen imagen = imagenService.actualizar(idImagen, file);
        personaje.setImagen(imagen);
        
        personajeRepository.save(personaje);
        
    }
    
}
