/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.DisneyAlkemy.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Fede
 */
@Entity
public class PeliculaOSerie {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @OneToOne
    private Imagen imagen;
    private String titulo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")      
    @javax.persistence.Temporal(TemporalType.DATE)
    private Date fechaDeCreacion;
    private String calificacion;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "pelicula_personajes", joinColumns = @JoinColumn(name = "pelicula_id"),
               inverseJoinColumns = @JoinColumn(name = "personaje_id"))
    private List<Personaje> personajes = new ArrayList<Personaje>(); 


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void setFechaDeCreacion(Date fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public List<Personaje> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(List<Personaje> personajes) {
        this.personajes = personajes;
    }

 

    
    
}
