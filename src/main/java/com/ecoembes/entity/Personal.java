package com.ecoembes.entity;

import java.util.List;
import java.util.Objects;

public class Personal {
	protected int idPersonal;
	protected String nombre;
	protected String correo;
	protected String contrasena;
	protected List<Sesion> sesiones;
	protected List<Asignacion> asignaciones;

	public Personal(int idPersonal, String nombre, String correo, String contrasena, List<Sesion> sesiones,
			List<Asignacion> asignaciones) {
		super();
		this.idPersonal = idPersonal;
		this.nombre = nombre;
		this.correo = correo;
		this.contrasena = contrasena;
		this.sesiones = sesiones;
		this.asignaciones = asignaciones;
	}

	public int getIdPersonal() {
		return idPersonal;
	}

	public void setIdPersonal(int idPersonal) {
		this.idPersonal = idPersonal;
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

	public List<Sesion> getSesiones() {
		return sesiones;
	}

	public void setSesiones(List<Sesion> sesiones) {
		this.sesiones = sesiones;
	}

	public List<Asignacion> getAsignaciones() {
		return asignaciones;
	}

	public void setAsignaciones(List<Asignacion> asignaciones) {
		this.asignaciones = asignaciones;
	}

	@Override
	public int hashCode() {
		return Objects.hash(asignaciones, contrasena, correo, idPersonal, nombre, sesiones);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Personal other = (Personal) obj;
		return Objects.equals(asignaciones, other.asignaciones) && Objects.equals(contrasena, other.contrasena)
				&& Objects.equals(correo, other.correo) && idPersonal == other.idPersonal
				&& Objects.equals(nombre, other.nombre) && Objects.equals(sesiones, other.sesiones);
	}

	
	
	

}
