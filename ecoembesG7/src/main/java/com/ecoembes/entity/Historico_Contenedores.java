package com.ecoembes.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;

@Entity
public class Historico_Contenedores {

    @Id
    private LocalDate fecha;
    @OneToMany
    private List<Contenedor> contenedores;
    @OneToOne
    @JoinColumn(name = "jornada_fecha") // FK hacia la jornada
    private Jornada jornada; // Cada histórico pertenece a una única jornada


    public Historico_Contenedores(LocalDate fecha, List<Contenedor> contenedores) {
        this.fecha = fecha;
        this.contenedores = contenedores;
    }

    public Historico_Contenedores() {}

    public LocalDate getFecha() {
        return fecha;
    }


    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public List<Contenedor> getContenedores() {
        return contenedores;
    }
    public void setContenedores(List<Contenedor> contenedores) {
        this.contenedores = contenedores;
    }
}
