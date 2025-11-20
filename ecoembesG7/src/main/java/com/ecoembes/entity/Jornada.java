package com.ecoembes.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Jornada {
	private static int contadorId = 0;
	protected int idJornada;
	protected Empleado asignadorPlanta;// Empleado que asigna los contenedores a la planta en cada jornada.
	protected List<Empleado> personal;	// Empleados asignados a la jornada.
	protected PlantaReciclaje plantaAsignada;
	protected List<Contenedor> contenedores;
	protected int numContenedores;
	protected double totalCapacidad; // Total de la capacidad que va a tener una planta en una jornada.
	protected LocalDate fechaJornada;
	
	public Jornada(Empleado asignadorPlanta, List<Empleado> personal, PlantaReciclaje plantaAsignada,
			List<Contenedor> contenedores, double totalCapacidad, LocalDate fechaJornada) {
		super();
		this.idJornada = contadorId++;
		this.asignadorPlanta = asignadorPlanta;
		this.personal = personal;
		this.plantaAsignada = plantaAsignada;
		this.contenedores = contenedores;
		this.numContenedores = contenedores.size();
		this.totalCapacidad = totalCapacidad;
		this.fechaJornada = fechaJornada;
	}

	
	public int getIdJornada() {
		return idJornada;
	}

	public void setIdJornada(int idJornada) {
		this.idJornada = idJornada;
	}

	public Empleado getAsignadorPlanta() {
		return asignadorPlanta;
	}

	public void setAsignadorPlanta(Empleado asignadorPlanta) {
		this.asignadorPlanta = asignadorPlanta;
	}

	public List<Empleado> getPersonal() {
		return personal;
	}

	public void setPersonal(List<Empleado> personal) {
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
	
	public int getNumContenedores() {
		return numContenedores;
	}

	public void setContenedores(List<Contenedor> contenedores) {
		this.contenedores = contenedores;
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

	public LocalDate getFechaJornada() {
		return fechaJornada;
	}

	public void setFechaJornada(LocalDate fechaJornada) {
		this.fechaJornada = fechaJornada;
		
		
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(asignadorPlanta, contenedores, fechaJornada, idJornada, numContenedores, personal, plantaAsignada,
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
		return Objects.equals(asignadorPlanta, other.asignadorPlanta)
				&& Objects.equals(contenedores, other.contenedores)
				&& Objects.equals(fechaJornada, other.fechaJornada) && idJornada == other.idJornada
				&& numContenedores == other.numContenedores && Objects.equals(personal, other.personal)
				&& Objects.equals(plantaAsignada, other.plantaAsignada)
				&& Double.doubleToLongBits(totalCapacidad) == Double.doubleToLongBits(other.totalCapacidad);
	}

	@Override
	public String toString() {
		return "Jornada [idJornada=" + idJornada + ", asignadorPlanta=" + asignadorPlanta + ", personal=" + personal + ", plantaAsignada=" + plantaAsignada
				+ ", contenedores=" + contenedores + ", numContenedores=" + numContenedores
				+ ", totalCapacidad=" + totalCapacidad + ", fechaJornada=" + fechaJornada + "]";
	}
}