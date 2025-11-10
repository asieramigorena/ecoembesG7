package com.ecoembes.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Jornada {
	protected int idJornada;
	protected Empleado personal;
	protected PlantaReciclaje plantaAsignada;
	protected List<Contenedor> contenedores;
	protected int numContenedores;
	protected double totalCapacidad;
	protected Date fechaJornada;
	
	public Jornada(int idJornada, Empleado personal, PlantaReciclaje plantaAsignada,
			List<Contenedor> contenedores, int numContenedores, double totalCapacidad, Date fechaJornada) {
		super();
		this.idJornada = idJornada;
		this.personal = personal;
		this.plantaAsignada = plantaAsignada;
		this.contenedores = contenedores;
		this.numContenedores = numContenedores;
		this.totalCapacidad = totalCapacidad;
		this.fechaJornada = fechaJornada;
	}

	public int getIdJornada() {
		return idJornada;
	}

	public void setIdJornada(int idJornada) {
		this.idJornada = idJornada;
	}

	public Empleado getPersonal() {
		return personal;
	}

	public void setPersonal(Empleado personal) {
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

	public Date getFechaJornada() {
		return fechaJornada;
	}

	public void setFechaJornada(Date fechaJornada) {
		this.fechaJornada = fechaJornada;
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(contenedores, fechaJornada, idJornada, numContenedores, personal, plantaAsignada,
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
		Jornada other = (Jornada) obj;
		return Objects.equals(contenedores, other.contenedores)
				&& Objects.equals(fechaJornada, other.fechaJornada) && idJornada == other.idJornada
				&& numContenedores == other.numContenedores && Objects.equals(personal, other.personal)
				&& Objects.equals(plantaAsignada, other.plantaAsignada)
				&& Double.doubleToLongBits(totalCapacidad) == Double.doubleToLongBits(other.totalCapacidad);
	}
	
	

}
