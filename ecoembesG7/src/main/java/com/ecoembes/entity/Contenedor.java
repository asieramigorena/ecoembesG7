package com.ecoembes.entity;

import java.util.Objects;
import com.ecoembes.entity.nivelLenado;
import jakarta.persistence.*;

@Entity
@Table(name = "contenedores")
public class Contenedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int idContenedor;
    @Column()
    protected String ubicacion;
    @Column()
    protected int codPostal;
    @Column()
    protected double capMaxima;
    @Enumerated(EnumType.STRING)
    protected nivelLenado nivelActual;
    @Column()
    protected int numEnvases;
    @Column()
    protected double nivelActualToneladas;



    public Contenedor(String ubicacion, int codPostal, double capMaxima) {
		super();
		this.ubicacion = ubicacion;
		this.codPostal = codPostal;
		this.capMaxima = capMaxima;
		this.nivelActual = nivelLenado.VERDE; 
	}

    public Contenedor() {

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

	public double getNivelActualToneladas() {
		return nivelActualToneladas;
	}

	public void setNivelActualToneladas(double nivelActualToneladas) {
		this.nivelActualToneladas = nivelActualToneladas;
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
