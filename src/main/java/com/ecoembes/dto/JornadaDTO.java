package com.ecoembes.dto;

import java.util.Date;
import java.util.List;

import com.ecoembes.entity.Contenedor;
import com.ecoembes.entity.Empleado;
import com.ecoembes.entity.PlantaReciclaje;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object para la entidad Jornada.")

public class JornadaDTO {
	private Empleado asignadorPlanta;// Empleado que asigna los contenedores a la planta en cada jornada.
	private PlantaReciclaje plantaAsignada;
	private List<Contenedor> contenedores;
	private int numContenedores;
	private double totalCapacidad; // Total de la capacidad que va a tener una planta en una jornada (en toneladas).
	private Date fechaJornada;

	public Empleado getAsignadorPlanta() {
		return asignadorPlanta;
	}

	public void setAsignadorPlanta(Empleado asignadorPlanta) {
		this.asignadorPlanta = asignadorPlanta;
	}

	public PlantaReciclaje getPlantaAsignada() {
		return plantaAsignada;
	}

	public void setPlantaAsignada(PlantaReciclaje plantaAsignada) {
		this.plantaAsignada = plantaAsignada;
	}

	public List<Contenedor> getContenedores() {
		return contenedores;
	}

	public void setContenedores(List<Contenedor> contenedores) {
		this.contenedores = contenedores;
	}

	public int getNumContenedores() {
		return numContenedores;
	}
	
	public void setNumContenedores(int numContenedores) {
		this.numContenedores = numContenedores;
	}

	public double getTotalCapacidad() {
		return totalCapacidad;
	}

	public void setTotalCapacidad(double totalCapacidad) {
		this.totalCapacidad = totalCapacidad;
	}

	public Date getFechaJornada() {
		return fechaJornada;
	}

	public void setFechaJornada(Date fechaJornada) {
		this.fechaJornada = fechaJornada;
	}

}