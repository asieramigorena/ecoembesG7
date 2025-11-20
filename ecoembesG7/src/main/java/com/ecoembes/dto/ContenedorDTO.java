package com.ecoembes.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object for Contenedor entity")
public class ContenedorDTO {
	public enum nivelLenado {
		VERDE, //Hay espacio disponible
		NARANJA, //Espacio limitado
		ROJO //Lleno
	}
	protected int idContenedor;
	protected String ubicacion;
	protected int codPostal;
	protected double capMaxima;
	protected nivelLenado nivelActual;
	protected int numEnvases;
	
	public ContenedorDTO() {}

	public ContenedorDTO(String ubicacion, int codPostal, double capMaxima) {
		super();
		
		this.ubicacion = ubicacion;
		this.codPostal = codPostal;
		this.capMaxima = capMaxima;
		
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
}
