package com.ecoembes.service;

import java.util.ArrayList;  

import org.springframework.stereotype.Service;

import com.ecoembes.dto.ContenedorDTO;
import com.ecoembes.dto.EmpleadoDTO;
import com.ecoembes.dto.ContenedorDTO.nivelLenado;
import com.ecoembes.entity.Contenedor;
import com.ecoembes.entity.Empleado;

@Service
public class EmpleadoService {
	private ArrayList<Empleado> empleados = new ArrayList<>();
	private static  ArrayList<Contenedor> contenedores = new ArrayList<>();
	
	public EmpleadoService() {
		empleados.add(new Empleado("Jose Maria", "josemari@correo.com", "contrasena1"));
		empleados.add(new Empleado("Ana Lopez", "analope@correo.com", "contrasena2"));
	}
	
	
	public EmpleadoDTO login(String correo, String contrasena) throws Exception {
		if (correo.isBlank() || contrasena.isBlank()) {
			throw new Exception("Credenciales invalidas");
		}
		for (Empleado empleado : empleados) {
			if (empleado.getCorreo().equals(correo)) {
				if (empleado.getToken() != null) {
					throw new Exception("El usuario " + empleado.getCorreo() + " ya tiene una sesion iniciada");
				}
				else if (empleado.getContrasena().equals(contrasena)) {
					empleado.setToken();
					return empleadoToDTO(empleado);
				}
				break;
			}
		}
		throw new Exception("Credenciales invalidas");
	}
	
	
	public void logout(String correo) throws Exception {
		for (Empleado empleado : empleados) {
			if (empleado.getCorreo().equals(correo)) {
				if (empleado.getToken() != null) {
					empleado.setTokenNull();
					return;
				} else {
					throw new Exception("No hay ninguna sesion iniciada para el usuario: " + correo);
				}
			}
		}
		throw new Exception("No se ha encontrado el empleado con correo: " + correo);
	}
	
	
	public static void crearContenedor( String ubicacion, int codPostal, double capMaxima) {
		contenedores.add(new Contenedor(ubicacion, codPostal, capMaxima));
	}
	
	public static ArrayList<Contenedor> getContenedores(){
		ArrayList<Contenedor> cont = new ArrayList<>();
		for (Contenedor c : contenedores) {
			cont.add(c);
		}
		return cont;
	}
	
	public static void setContenedores(ArrayList<Contenedor> cont){
		contenedores.clear();
		for (Contenedor c : cont) {
			contenedores.add(c);
		}
	}
	
	
	public static ArrayList<ContenedorDTO> getContsPorZona (int codPostal) {
		ArrayList<ContenedorDTO> listaConts = new ArrayList<ContenedorDTO>();
		for(Contenedor cont : contenedores) {
			if(cont.getCodPostal() == codPostal) {
				listaConts.add(eontenedorToDTO(cont));
			}
		}	
		return listaConts;
	}
	
	
	private EmpleadoDTO empleadoToDTO (Empleado empleado) {
		return new EmpleadoDTO(empleado.getCorreo());
	}
	
	public static ContenedorDTO eontenedorToDTO(Contenedor contenedor) {
	    ContenedorDTO dto = new ContenedorDTO();
	    dto.setIdContenedor(contenedor.getIdContenedor());
	    dto.setUbicacion(contenedor.getUbicacion());
	    dto.setCodPostal(contenedor.getCodPostal());
	    dto.setCapMaxima(contenedor.getCapMaxima());
	    switch (contenedor.getNivelActual()) {
	    case VERDE:
	        dto.setNivelActual(ContenedorDTO.nivelLenado.VERDE);
	        break;
	    case NARANJA:
	        dto.setNivelActual(ContenedorDTO.nivelLenado.NARANJA);
	        break;
	    case ROJO:
	        dto.setNivelActual(ContenedorDTO.nivelLenado.ROJO);
	        break;
	}
	    dto.setNumEnvases(contenedor.getNumEnvases());
	    return dto;
	}
}




