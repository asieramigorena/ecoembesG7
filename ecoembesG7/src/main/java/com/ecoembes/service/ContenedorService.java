package com.ecoembes.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SortedMap;

import com.ecoembes.dao.ContenedorDAO;
import com.ecoembes.dao.EmpleadoDAO;
import com.ecoembes.dao.Historico_ContenedoresDAO;
import com.ecoembes.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecoembes.dto.ContenedorDTO;

@Service
public class ContenedorService {

    @Autowired
    private ContenedorDAO contenedorDAO;

    @Autowired
    private EmpleadoDAO empleadoDAO;

    @Autowired
    private Historico_ContenedoresDAO historico_contenedoresDAO;

    public void actualizarContenedores(){

        Iterable<Contenedor> iterable = contenedorDAO.findAll();
        List<Contenedor> contenedores = new ArrayList<>();
        iterable.forEach(contenedores::add);

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
            contenedorDAO.save(cont);
		}
        alertaSaturacion();
	}
	
	public void crearContenedor( String ubicacion, int codPostal, double capMaxima) {
    Contenedor cont = new Contenedor(ubicacion, codPostal, capMaxima);
        contenedorDAO.save(cont);
	}

	public ArrayList<ContenedorDTO> getContsPorZona (int codPostal) {
		ArrayList<ContenedorDTO> listaConts = new ArrayList<>();

        Iterable<Contenedor> iterable = contenedorDAO.findAll();
        List<Contenedor> contenedores = new ArrayList<>();
        iterable.forEach(contenedores::add);

		for(Contenedor cont : contenedores) {
			if(cont.getCodPostal() == codPostal) {
				listaConts.add(contenedorToDTO(cont));
			}
		}	
		return listaConts;
	}

    public ArrayList<ContenedorDTO> getContsPorFecha(int idContenedor, LocalDate fechaInicio, LocalDate fechaFin){
        ArrayList<ContenedorDTO> listaConts = new ArrayList<>();

        Iterable<Historico_Contenedores> iterable = historico_contenedoresDAO.findAll();
        List<Historico_Contenedores> historicos = new ArrayList<>();
        iterable.forEach(historicos::add);

        for (Historico_Contenedores hc : historicos) {
            if (!hc.getFecha().isBefore(fechaInicio) && !hc.getFecha().isAfter(fechaFin)) {
                for(Contenedor contenedor : hc.getContenedores()) {
                    if(contenedor.getIdContenedor() == idContenedor) {
                        listaConts.add(contenedorToDTO(contenedor));
                    }
                }
            }
        }

        return listaConts;
    }

    public void alertaSaturacion() {
		int contadorRojo = 0;

        Iterable<Contenedor> iterable = contenedorDAO.findAll();
        List<Contenedor> contenedores = new ArrayList<>();
        iterable.forEach(contenedores::add);

		for(Contenedor cont : contenedores) {
			if(cont.getNivelActual() == nivelLenado.ROJO) {
				contadorRojo++;
			}
		}
		if ((double) contadorRojo / contenedores.size() > 0.8) {

            Iterable<Empleado> iterable2 = empleadoDAO.findAll();
            List<Empleado> empleados = new ArrayList<>();
            iterable2.forEach(empleados::add);

			for(Empleado emp : empleados) {
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