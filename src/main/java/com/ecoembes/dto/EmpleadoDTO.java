package com.ecoembes.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object for Empleado entity")
public class EmpleadoDTO {
	private int idPersonal;
	private String nombre;
	private String correo;
	private String contrasena;
	
	public EmpleadoDTO() {}
	
	public EmpleadoDTO(int idPersonal, String nombre, String correo, String contrasena) {
		this.idPersonal = idPersonal;
		this.nombre = nombre;
		this.correo = correo;
		this.contrasena = contrasena;
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

	public void setContrasena(String contrasena2) {
		// TODO Auto-generated method stub
		
	}
}
