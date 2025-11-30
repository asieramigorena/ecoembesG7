package com.plassb.model.entitys;


import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
@Table(name = "CAPACIDAD")
public class Capacidad implements Serializable {
	
	private static final long serialVersionUID = 1L;
	

    @Id
    private LocalDate fecha;
    private double capacidad;
    
    

    // Getters y Setters
    public LocalDate getId() { return fecha; }
    public void setId(LocalDate fecha) { this.fecha = fecha; }
    public Double getCapacidad() { return capacidad; }
    public void setCapacidad(double capacidad) { this.capacidad = capacidad; }


}
