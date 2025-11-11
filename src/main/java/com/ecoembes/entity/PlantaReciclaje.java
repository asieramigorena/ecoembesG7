package com.ecoembes.entity;

import java.util.Objects;

public class PlantaReciclaje {
	protected static int contadorId = 0;
	protected int id;
	protected String nombre;
	protected double capTotal;

	public PlantaReciclaje(String nombre, double capTotal) {
		super();
		this.id = contadorId++;
		this.nombre = nombre;
		this.capTotal = capTotal;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getCapTotal() {
		return capTotal;
	}

	public void setCapTotal(double capTotal) {
		this.capTotal = capTotal;
	}

	@Override
	public int hashCode() {
		return Objects.hash(capTotal, id, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlantaReciclaje other = (PlantaReciclaje) obj;
		return Double.doubleToLongBits(capTotal) == Double.doubleToLongBits(other.capTotal) && id == other.id
				&& Objects.equals(nombre, other.nombre);
	}
}
