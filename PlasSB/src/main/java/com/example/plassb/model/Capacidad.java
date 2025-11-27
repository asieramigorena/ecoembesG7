package com.example.plassb.model;


import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Capacidad {

    @Id
    private LocalDate id;
    private double capacidad;

    // Getters y Setters
    public LocalDate getId() { return id; }
    public void setId(LocalDate id) { this.id = id; }
    public Double getCapacidad() { return capacidad; }
    public void setCapacidad(double capacidad) { this.capacidad = capacidad; }


}
