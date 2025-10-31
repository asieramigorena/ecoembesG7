package com.ecoembes.entity;

public class PlantaReciclaje {
	protected int id;
	protected String nombre;
	protected double capTotal;
	
	public PlantaReciclaje(int id, String nombre, double capTotal) {
		super();
		this.id = id;
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
	
	
	
	

}
