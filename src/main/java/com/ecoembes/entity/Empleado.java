package com.ecoembes.entity;

import java.time.LocalTime;
import java.util.Objects;

public class Empleado {
	private static int contadorId = 0;
	protected int idPersonal;
	protected String nombre;
	protected String correo;
	protected String contrasena;
	private LocalTime token;

	public Empleado(String nombre, String correo, String contrasena) {
		super();
		this.idPersonal = contadorId++;
		this.nombre = nombre;
		this.correo = correo;
		this.contrasena = contrasena;
		this.token = null;
	}

	public int getIdPersonal() {
		return idPersonal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	public LocalTime getToken() {
		return token;
	}
	
	public void setToken() {
		this.token = LocalTime.now();
	}

	public void setTokenNull() {
		this.token = null;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(contrasena, correo, idPersonal, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empleado other = (Empleado) obj;
		return Objects.equals(contrasena, other.contrasena)
				&& Objects.equals(correo, other.correo) && idPersonal == other.idPersonal
				&& Objects.equals(nombre, other.nombre);
	}

	

	
	
	

}
