package com.ecoembes.service;

import java.util.ArrayList;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.ecoembes.dto.ContenedorDTO;
import com.ecoembes.entity.Contenedor;

@Service
public class ContenedorService {
	private ArrayList<Contenedor> contenedores = new ArrayList<>();

	public ContenedorService() {
		contenedores.add(new Contenedor("Calle Falsa 123", 28080, 1000));
		contenedores.add(new Contenedor("Avenida Siempre Viva 742", 28080, 1500));
		contenedores.add(new Contenedor("Plaza Mayor 1", 28080, 2000));
	}
	
	public void actualizarContenedores(){
		contenedores = getContenedores();
		
		for (Contenedor cont : contenedores) {
			Random random = new Random();
			int llenado = random.nextInt(2 - 0 + 1);
			int numEnvases = (llenado + 1) * 300;
			cont.setNumEnvases(numEnvases);
			 switch (llenado) {
			    case 0:
			        cont.setNivelActual(Contenedor.nivelLenado.VERDE);
			        break;
			    case 1:
			    	cont.setNivelActual(Contenedor.nivelLenado.NARANJA);
			        break;
			    case 2:
			    	cont.setNivelActual(Contenedor.nivelLenado.ROJO);
			        break;
			}
			 setContenedores(contenedores);
		}	
	}
	
	public void crearContenedor( String ubicacion, int codPostal, double capMaxima) {
		contenedores.add(new Contenedor(ubicacion, codPostal, capMaxima));
	}
	
	public ArrayList<Contenedor> getContenedores(){
		ArrayList<Contenedor> cont = new ArrayList<>();
		for (Contenedor c : contenedores) {
			cont.add(c);
		}
		return cont;
	}
	
	public void setContenedores(ArrayList<Contenedor> cont){
		contenedores.clear();
		for (Contenedor c : cont) {
			contenedores.add(c);
		}
	}
	
	public ArrayList<ContenedorDTO> getContsPorZona (int codPostal) {
		ArrayList<ContenedorDTO> listaConts = new ArrayList<ContenedorDTO>();
		for(Contenedor cont : contenedores) {
			if(cont.getCodPostal() == codPostal) {
				listaConts.add(contenedorToDTO(cont));
			}
		}	
		return listaConts;
	}
	
	public ContenedorDTO contenedorToDTO(Contenedor contenedor) {
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