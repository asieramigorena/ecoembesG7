package com.ecoembes.dto;

import java.util.Objects;

public class capacidadPlantasDTO {
	private String nombrePlanta;
	private double capacidadTotal;

	public capacidadPlantasDTO(String nombrePlanta, double capacidadTotal) {
		this.nombrePlanta = nombrePlanta;
		this.capacidadTotal = capacidadTotal;
	}

	public String getNombrePlanta() {
		return nombrePlanta;
	}

	public void setNombrePlanta(String nombrePlanta) {
		this.nombrePlanta = nombrePlanta;
	}

	public double getCapacidadTotal() {
		return capacidadTotal;
	}

	public void setCapacidadTotal(double capacidadTotal) {
		this.capacidadTotal = capacidadTotal;
	}

	@Override
	public int hashCode() {
		return Objects.hash(capacidadTotal, nombrePlanta);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		capacidadPlantasDTO other = (capacidadPlantasDTO) obj;
		return Double.doubleToLongBits(capacidadTotal) == Double.doubleToLongBits(other.capacidadTotal)
				&& Objects.equals(nombrePlanta, other.nombrePlanta);
	}
}