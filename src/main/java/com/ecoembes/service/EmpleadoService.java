package com.ecoembes.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.ecoembes.dto.EmpleadoDTO;
import com.ecoembes.entity.Empleado;

@Service
public class EmpleadoService {
	private ArrayList<Empleado> empleados = new ArrayList<>();
	
	public EmpleadoService() {
		empleados.add(new Empleado("Jose Maria", "josemari@correo.com", "contrasena1"));
		empleados.add(new Empleado("Ana Lopez", "analope@correo.com", "contrasena2"));
	}
	
	public EmpleadoDTO login(String correo, String contrasena) throws Exception {
		if (correo.isBlank() || contrasena.isBlank()) {
			throw new Exception("Credenciales invalidas");
		}
		for (Empleado empleado : empleados) {
			if (empleado.getToken() != null) {
				throw new Exception("El usuario " + empleado.getCorreo() + " ya tiene una sesion iniciada");
			}
			else if (empleado.getCorreo().equals(correo) && empleado.getContrasena().equals(contrasena)) {
				empleado.setToken();
				return toDTO(empleado);
			}
		}
		throw new Exception("Credenciales invalidas");
	}
	
	public void logout(String correo) throws Exception {
		for (Empleado empleado : empleados) {
			if (empleado.getToken() != null && empleado.getCorreo().equals(correo)) {
				empleado.setTokenNull();
				return;
			} else if (empleado.getCorreo().equals(correo)) {
				throw new Exception("No hay ninguna sesion iniciada para el usuario: " + correo);
			}
		}
		throw new Exception("No hay ninguna sesion iniciada");
	}
	
	private EmpleadoDTO toDTO (Empleado empleado) {
		EmpleadoDTO dto = new EmpleadoDTO();
		dto.setIdPersonal(empleado.getIdPersonal());
		dto.setNombre(empleado.getNombre());
		dto.setCorreo(empleado.getCorreo());
		dto.setContrasena(empleado.getContrasena());
		return dto;
	}
}
