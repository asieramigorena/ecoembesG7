package com.ecoembes.entity;

import java.util.Objects;

public class Camion {
	protected int idCamion;
	protected String matricula;
	protected double capacidad;
	protected Ruta rutaAsignada;

	public Camion(int idCamion, String matricula, double capacidad, Ruta rutaAsignada) {
		super();
		this.idCamion = idCamion;
		this.matricula = matricula;
		this.capacidad = capacidad;
		this.rutaAsignada = rutaAsignada;
	}
	
	
	public int getIdCamion() {
		return idCamion;
	}

	public void setIdCamion(int idCamion) {
		this.idCamion = idCamion;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public double getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(double capacidad) {
		this.capacidad = capacidad;
	}

	public Ruta getRutaAsignada() {
		return rutaAsignada;
	}

	public void setRutaAsignada(Ruta rutaAsignada) {
		this.rutaAsignada = rutaAsignada;
	}


	@Override
	public int hashCode() {
		return Objects.hash(capacidad, idCamion, matricula, rutaAsignada);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Camion other = (Camion) obj;
		return Double.doubleToLongBits(capacidad) == Double.doubleToLongBits(other.capacidad)
				&& idCamion == other.idCamion && Objects.equals(matricula, other.matricula)
				&& Objects.equals(rutaAsignada, other.rutaAsignada);
	}
	

}
