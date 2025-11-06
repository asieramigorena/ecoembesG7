package com.ecoembes.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Asignacion {
	protected int idAsignacion;
	protected Personal personal;
	protected PlantaReciclaje plantaAsignada;
	protected List<Contenedor> contenedores;
	protected int numContenedores;
	protected double totalCapacidad;
	protected Date fechaAsignacion;
	
	public Asignacion(int idAsignacion, Personal personal, PlantaReciclaje plantaAsignada,
			List<Contenedor> contenedores, int numContenedores, double totalCapacidad, Date fechaAsignacion) {
		super();
		this.idAsignacion = idAsignacion;
		this.personal = personal;
		this.plantaAsignada = plantaAsignada;
		this.contenedores = contenedores;
		this.numContenedores = numContenedores;
		this.totalCapacidad = totalCapacidad;
		this.fechaAsignacion = fechaAsignacion;
	}

	public int getIdAsignacion() {
		return idAsignacion;
	}

	public void setIdAsignacion(int idAsignacion) {
		this.idAsignacion = idAsignacion;
	}

	public Personal getPersonal() {
		return personal;
	}

	public void setPersonal(Personal personal) {
		this.personal = personal;
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

	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contenedores, fechaAsignacion, idAsignacion, numContenedores, personal, plantaAsignada,
				totalCapacidad);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Asignacion other = (Asignacion) obj;
		return Objects.equals(contenedores, other.contenedores)
				&& Objects.equals(fechaAsignacion, other.fechaAsignacion) && idAsignacion == other.idAsignacion
				&& numContenedores == other.numContenedores && Objects.equals(personal, other.personal)
				&& Objects.equals(plantaAsignada, other.plantaAsignada)
				&& Double.doubleToLongBits(totalCapacidad) == Double.doubleToLongBits(other.totalCapacidad);
	}
	
	

}
