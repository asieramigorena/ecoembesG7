package com.ecoembes.dto;

public class CapacidadResponse {

    private String id;        // o LocalDate
    private double capacidad;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(double capacidad) {
        this.capacidad = capacidad;
    }
}

