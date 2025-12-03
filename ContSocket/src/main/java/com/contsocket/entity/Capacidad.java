package com.contsocket.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "CAPACIDAD")
public class Capacidad implements Serializable {
	
	@Serial
    private static final long serialVersionUID = 1L;
	

    @Id
    private LocalDate fecha;
    private double capacidad;

    public Capacidad() {

    }

    public Capacidad(LocalDate fecha, double capacidad) {
        this.fecha = fecha;
        this.capacidad = capacidad;
    }

    // Getters y Setters
    public LocalDate getId() { return fecha; }
    public void setId(LocalDate fecha) { this.fecha = fecha; }
    public Double getCapacidad() { return capacidad; }
    public void setCapacidad(double capacidad) { this.capacidad = capacidad; }


}
