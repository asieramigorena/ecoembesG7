package com.ecoembes.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.SortedMap;

import com.ecoembes.entity.*;
import org.springframework.stereotype.Service;

import com.ecoembes.dto.ContenedorDTO;

@Service
public class ContenedorService {
	private static ArrayList<Contenedor> contenedores = new ArrayList<>();

	public ContenedorService() {
		contenedores.add(new Contenedor("Calle Falsa 123", 28080, 1000));
		contenedores.add(new Contenedor("Avenida Siempre Viva 742", 28080, 1500));
		contenedores.add(new Contenedor("Plaza Mayor 1", 12001, 2000));
        contenedores.add(new Contenedor("Calle Gran Vía 45", 28013, 1300));
        contenedores.add(new Contenedor("Avenida del Cid 8", 46001, 900));
        contenedores.add(new Contenedor("Calle San Francisco 22", 48003, 1700));
        contenedores.add(new Contenedor("Paseo de la Castellana 210", 28046, 2200));
	}
	
	public void actualizarContenedores(){
		
		for (Contenedor cont : contenedores) {
			Random random = new Random();
			int llenado = random.nextInt(2 + 1);
			int numEnvases = (llenado + 1) * 300;
			cont.setNumEnvases(numEnvases);
			cont.setNivelActualToneladas(random.nextDouble(0, cont.getCapMaxima()));
			 switch (llenado) {
			    case 0:
			        cont.setNivelActual(nivelLenado.VERDE);
			        break;
			    case 1:
			    	cont.setNivelActual(nivelLenado.NARANJA);
			        break;
			    case 2:
			    	cont.setNivelActual(nivelLenado.ROJO);
			        break;
			}
		}
        alertaSaturacion();
	}
	
	public void crearContenedor( String ubicacion, int codPostal, double capMaxima) {
		contenedores.add(new Contenedor(ubicacion, codPostal, capMaxima));
	}
	
	public static ArrayList<Contenedor> getContenedores(){
		return contenedores;
	}
	
	public void setContenedores(ArrayList<Contenedor> cont){
        contenedores.addAll(cont);
	}
	
	public ArrayList<ContenedorDTO> getContsPorZona (int codPostal) {
		ArrayList<ContenedorDTO> listaConts = new ArrayList<>();
		for(Contenedor cont : contenedores) {
			if(cont.getCodPostal() == codPostal) {
				listaConts.add(contenedorToDTO(cont));
			}
		}	
		return listaConts;
	}
	
	public ArrayList<ContenedorDTO> getContsPorFecha(int idContenedor, LocalDate fechaInicio, LocalDate fechaFin){
		ArrayList<ContenedorDTO> listaConts = new ArrayList<>();
        Historico_Contenedores historico= JornadaService.getHistoricoContenedores();

        SortedMap<LocalDate, ArrayList<Contenedor>> rango = historico.getLista().subMap(fechaInicio, fechaFin.plusDays(1));
        for(LocalDate fecha : rango.keySet()) {
            for(Contenedor contenedor : rango.get(fecha)) {
                listaConts.add(contenedorToDTO(contenedor));
            }
        }
		return listaConts;
	}
	
	
	public void alertaSaturacion() {
		int contadorRojo = 0;
		for(Contenedor cont : contenedores) {
			if(cont.getNivelActual() == nivelLenado.ROJO) {
				contadorRojo++;
			}
		}
		if ((double) contadorRojo / contenedores.size() > 0.8) {
			for(Empleado emp : EmpleadoService.getEmpleados()) {
				System.out.println("Alerta enviada a: " + emp.getCorreo());
			}
		}
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