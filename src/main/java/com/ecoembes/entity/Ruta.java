package com.ecoembes.entity;

import java.util.List;
import java.util.Objects;

public class Ruta {
	protected int idRuta;
	protected double duracion;
	protected double distancia;
	protected Camion camion;
	protected List<Contenedor> contenedores;

	public Ruta(int idRuta, double duracion, double distancia, Camion camion, List<Contenedor> contenedores) {
		super();
		this.idRuta = idRuta;
		this.duracion = duracion;
		this.distancia = distancia;
		this.camion = camion;
		this.contenedores = contenedores;
	}

	public int getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(int idRuta) {
		this.idRuta = idRuta;
	}

	public double getDuracion() {
		return duracion;
	}

	public void setDuracion(double duracion) {
		this.duracion = duracion;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	public Camion getCamion() {
		return camion;
	}

	public void setCamion(Camion camion) {
		this.camion = camion;
	}

	public List<Contenedor> getContenedores() {
		return contenedores;
	}

	public void setContenedores(List<Contenedor> contenedores) {
		this.contenedores = contenedores;
	}

	@Override
	public int hashCode() {
		return Objects.hash(camion, contenedores, distancia, duracion, idRuta);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ruta other = (Ruta) obj;
		return Objects.equals(camion, other.camion) && Objects.equals(contenedores, other.contenedores)
				&& Double.doubleToLongBits(distancia) == Double.doubleToLongBits(other.distancia)
				&& Double.doubleToLongBits(duracion) == Double.doubleToLongBits(other.duracion)
				&& idRuta == other.idRuta;
	}
	

}
