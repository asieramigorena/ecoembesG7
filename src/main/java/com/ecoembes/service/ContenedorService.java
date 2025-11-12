package com.ecoembes.service;

import java.util.ArrayList;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.ecoembes.entity.Contenedor;

@Service
public class ContenedorService {
	
	
	public static void actualizarContenedores(){
		ArrayList<Contenedor> contenedores = new ArrayList<>();
		contenedores = EmpleadoService.getContenedores();
		
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
			 EmpleadoService.setContenedores(contenedores);
		}	
	}

	
	
	
	
	
	
	

}