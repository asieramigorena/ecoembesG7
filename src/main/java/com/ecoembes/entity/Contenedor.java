package com.ecoembes.entity;

import java.util.Objects;

public class Contenedor {
	
	public enum nivelLenado {
		VERDE, //Hay espacio disponible
		NARANJA, //Espacio limitado
		ROJO //Lleno
	}
	
	private static int contadorId = 0;
	protected int idContenedor;
	protected String ubicacion;
	protected int codPostal;
	protected double capMaxima;
	protected nivelLenado nivelActual;
	protected int numEnvases;
	
	public Contenedor(String ubicacion, int codPostal, double capMaxima) {
		super();
		this.idContenedor = contadorId++;
		this.ubicacion = ubicacion;
		this.codPostal = codPostal;
		this.capMaxima = capMaxima;
		this.nivelActual = nivelLenado.VERDE; //Valor por defecto
	}

	public int getIdContenedor() {
		return idContenedor;
	}

	public void setIdContenedor(int idContenedor) {
		this.idContenedor = idContenedor;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public int getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(int codPostal) {
		this.codPostal = codPostal;
	}

	public double getCapMaxima() {
		return capMaxima;
	}

	public void setCapMaxima(double capMaxima) {
		this.capMaxima = capMaxima;
	}

	public nivelLenado getNivelActual() {
		return nivelActual;
	}

	public void setNivelActual(nivelLenado nivelActual) {
		this.nivelActual = nivelActual;
	}

	public int getNumEnvases() {
		return numEnvases;
	}

	public void setNumEnvases(int numEnvases) {
		this.numEnvases = numEnvases;
	}

	@Override
	public int hashCode() {
		return Objects.hash(capMaxima, codPostal, idContenedor, nivelActual, numEnvases, ubicacion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contenedor other = (Contenedor) obj;
		return Double.doubleToLongBits(capMaxima) == Double.doubleToLongBits(other.capMaxima)
				&& codPostal == other.codPostal && idContenedor == other.idContenedor
				&& nivelActual == other.nivelActual && numEnvases == other.numEnvases
				&& Objects.equals(ubicacion, other.ubicacion);
	}
}
