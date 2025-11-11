package com.ecoembes.service;

import java.util.ArrayList; 

import org.springframework.stereotype.Service;

import com.ecoembes.dto.ContenedorDTO;
import com.ecoembes.entity.Contenedor;

@Service
public class ContenedorService {
	private ArrayList<Contenedor> contenedores = new ArrayList<>();
	
	public ContenedorService() {
		for (int i = 1; i <= 15; i++) {
			
            Contenedor c = new Contenedor(
                "Ubicacion " + i, 
                28000 + i, 
                100.0, 
                Contenedor.nivelLenado.NARANJA,
                i * 2
            );
            contenedores.add(c);
        }
		
	}
	
	
	public ArrayList<ContenedorDTO> getContsPorZona (int codPostal) {
		ArrayList<ContenedorDTO> listaConts = new ArrayList<ContenedorDTO>();
		for(Contenedor cont : contenedores) {
			if(cont.getCodPostal() == codPostal) {
				listaConts.add(toDTO(cont));
			}
		}	
		return listaConts;
	}
	
	
	public ContenedorDTO toDTO(Contenedor contenedor) {
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