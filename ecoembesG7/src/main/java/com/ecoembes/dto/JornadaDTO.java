package com.ecoembes.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object para la entidad Jornada.")

public class JornadaDTO {
	private String correoAsignador;  // Solo el correo del empleado
	private String nombrePlanta;     // Solo el nombre de la planta
	private double totalCapacidad;   // Total de la capacidad que va a tener una planta en una jornada (en toneladas).
	private LocalDate fechaJornada;

	public String getCorreoAsignador() {
		return correoAsignador;
	}

	public void setCorreoAsignador(String correoAsignador) {
		this.correoAsignador = correoAsignador;
	}

	public String getNombrePlanta() {
		return nombrePlanta;
	}

	public void setNombrePlanta(String nombrePlanta) {
		this.nombrePlanta = nombrePlanta;
	}

	public double getTotalCapacidad() {
		return totalCapacidad;
	}

	public void setTotalCapacidad(double totalCapacidad) {
		this.totalCapacidad = totalCapacidad;
	}

	public LocalDate getFechaJornada() {
		return fechaJornada;
	}

	public void setFechaJornada(LocalDate fechaJornada) {
		this.fechaJornada = fechaJornada;
	}

}