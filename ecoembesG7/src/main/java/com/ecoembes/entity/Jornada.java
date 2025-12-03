package com.ecoembes.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Jornada {
	private static int contadorId = 0;
	protected int idJornada;
	protected Empleado asignadorPlanta;// Empleado que asigna los contenedores a la planta en cada jornada.
	protected List<Empleado> personal;	// Empleados asignados a la jornada.
	protected PlantaReciclaje plantaAsignada;
//	protected double totalCapacidad;  Total de la capacidad que va a tener una planta en una jornada. YA NO HACE FALTA.
	
	protected LocalDate fechaJornada;
	
	public Jornada(Empleado asignadorPlanta, List<Empleado> personal, PlantaReciclaje plantaAsignada,
			 double totalCapacidad, LocalDate fechaJornada) {
		super();
		this.idJornada = contadorId++;
		this.asignadorPlanta = asignadorPlanta;
		this.personal = personal;
		this.plantaAsignada = plantaAsignada;
//		this.totalCapacidad = totalCapacidad;
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

//	public double getTotalCapacidad() {
//		return totalCapacidad;
//	}
//
//	public void setTotalCapacidad(double totalCapacidad) {
//		this.totalCapacidad = totalCapacidad;
//	}

	public LocalDate getFechaJornada() {
		return fechaJornada;
	}

	public void setFechaJornada(LocalDate fechaJornada) {
		this.fechaJornada = fechaJornada;
		
		
	}


	@Override
	public int hashCode() {
		return Objects.hash(asignadorPlanta, fechaJornada, idJornada, personal, plantaAsignada);
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
				&& Objects.equals(fechaJornada, other.fechaJornada) && idJornada == other.idJornada
				&& Objects.equals(personal, other.personal) && Objects.equals(plantaAsignada, other.plantaAsignada);
	}


	@Override
	public String toString() {
		return "Jornada [idJornada=" + idJornada + ", asignadorPlanta=" + asignadorPlanta + ", personal=" + personal
				+ ", plantaAsignada=" + plantaAsignada + ", fechaJornada=" + fechaJornada + "]";
	}



	


}