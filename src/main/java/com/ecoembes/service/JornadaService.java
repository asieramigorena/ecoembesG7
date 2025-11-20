package com.ecoembes.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecoembes.dto.JornadaDTO;
import com.ecoembes.dto.capacidadPlantasDTO;
import com.ecoembes.entity.Contenedor;
import com.ecoembes.entity.Empleado;
import com.ecoembes.entity.Jornada;
import com.ecoembes.entity.PlantaReciclaje;

@Service
public class JornadaService {
	private static ArrayList<Jornada> jornadas = new ArrayList<>();
	
	public JornadaService() {

        // --- EMPLEADOS BASE ---
        Empleado ana = new Empleado("Ana García", "ana.garcia@eco.com", "pass123");
        Empleado javier = new Empleado("Javier Ruiz", "javier.ruiz@eco.com", "pass456");
        Empleado mario = new Empleado("Mario Vidal", "mario.vidal@eco.com", "pass789");
        Empleado luis = new Empleado("Luis Pérez", "luis.perez@eco.com", "lpass");
        Empleado marta = new Empleado("Marta Soto", "marta.soto@eco.com", "mpass");
        Empleado elena = new Empleado("Elena Ríos", "elena.rios@eco.com", "epass");
        Empleado carlos = new Empleado("Carlos Gil", "carlos.gil@eco.com", "cpass");
        Empleado laura = new Empleado("Laura Díaz", "laura.diaz@eco.com", "lapass");
        
        // --- PLANTAS BASE ---
        PlantaReciclaje planta1 = new PlantaReciclaje("Planta Norte - Polímeros");
        PlantaReciclaje planta2 = new PlantaReciclaje("Planta Central - Cristal");
        
        // --- CREACIÓN DE LAS 10 JORNADAS ---

     // Jornada 1: Plásticos (Ana)
        jornadas.add(new Jornada(
            ana,
            Arrays.asList(luis, marta),
            planta1,
            Arrays.asList(
                
                new Contenedor("Calle Plástico PE", 28001, 2500.0), 
                new Contenedor("Avenida Plástico PET", 28001, 2000.0),
                new Contenedor("Avenida Plástico PVC", 28002, 1500.5)
            ),
            600000.50,
            LocalDate.of(2025, 11, 13) 
        ));

        // Jornada 2: Vidrio (Javier)
        jornadas.add(new Jornada(
            javier,
                List.of(elena),
            planta2,
            Arrays.asList(
                new Contenedor("Calle Vidrio Transparente", 41005, 3000.0),
                new Contenedor("Avenida Vidrio Oscuro", 41005, 1500.0)
            ),
            450000.00,
            LocalDate.of(125, 10, 13) 
        ));

        // Jornada 3: Metales (Ana)
        jornadas.add(new Jornada(
            ana,
            Arrays.asList(carlos, laura),
            planta1,
            Arrays.asList(
                new Contenedor("Avenida Acero", 28003, 4000.0),
                new Contenedor("Avenida Aluminio", 28003, 3000.75),
                new Contenedor("Calle Cobre", 28004, 2200.0)
            ),
            920000.75,
            LocalDate.of(2025, 11, 12) 
        ));

        // Jornada 4: Papel (Mario)
        jornadas.add(new Jornada(
            mario,
            Arrays.asList(luis, elena),
            planta2,
            Arrays.asList(
                new Contenedor("Calle Papel Blanco", 41006, 2500.0),
                new Contenedor("Avenida Cartón", 41006, 2800.2),
                new Contenedor("Calle Revistas", 41007, 500.0)
            ),
            580000.20,
            LocalDate.of(2025, 11, 12) 
        ));

        // Jornada 5: Orgánicos (Javier)
        jornadas.add(new Jornada(
            javier,
            Arrays.asList(marta, carlos),
            planta1,
            Arrays.asList(
                new Contenedor("Avenida Orgánico A", 28005, 2000.9),
                new Contenedor("Calle Orgánico B", 28005, 1100.0)
            ),
            310000.90,
            LocalDate.of(2025, 11, 7) 
        ));

        // Jornada 6: Electrónicos (Mario)
        jornadas.add(new Jornada(
            mario,
                List.of(laura),
            planta2,
            Arrays.asList(
                new Contenedor("Avenida RAEE Pequeño", 41008, 1000.35),
                new Contenedor("Calle RAEE Grande", 41008, 800.0)
            ),
            180000.35,
            LocalDate.of(2025, 11, 9) 
        ));

        // Jornada 7: Plásticos Livianos (Ana)
        jornadas.add(new Jornada(
            ana,
                List.of(luis),
            planta1,
            Arrays.asList(
                new Contenedor("Calle Film Plástico", 28006, 1000.0),
                new Contenedor("Calle Bolsas", 28006, 1000.1),
                new Contenedor("Avenida Envases Ligeros", 28007, 500.0)
            ),
            250000.10,
            LocalDate.of(2025, 11, 8) 
        ));
	}
	
	public static ArrayList<Jornada> getJornadas() {
		return jornadas;
	}
	
	public ArrayList<capacidadPlantasDTO> capacidadPlantas(LocalDate fecha) {
		ArrayList<capacidadPlantasDTO> capacidades = new ArrayList<>();
		for (Jornada jornada : jornadas) {
			if (jornada.getFechaJornada().equals(fecha)) {
				capacidades.add(new capacidadPlantasDTO(jornada.getPlantaAsignada().getNombre(), jornada.getTotalCapacidad()) );
			}
		}
		return capacidades;
	}

	
	public JornadaDTO asignarContenedores(Jornada jornada, int idContenedor) throws IOException{

        for (Contenedor cont : ContenedorService.getContenedores()) {
            if (cont.getIdContenedor() == idContenedor) {
					
                if (jornada.getTotalCapacidad() < cont.getNivelActualToneladas()) {
                    throw new IOException("No se puede asignar el contenedor. Capacidad total de la planta superada.");
                } else {
                    jornada.setTotalCapacidad(jornada.getTotalCapacidad() - cont.getNivelActualToneladas());
                    jornada.getContenedores().add(cont);
                }

            }
				
        }
		
		return jornadaToDTO(jornada);
		
	}
	
	public Jornada getJornadaById(int id) {
		
		for (Jornada jornada : jornadas) {
			if (jornada.getIdJornada() == id) {
				return jornada;
			}
		}	
		return null;
		
	}
	
	  public static JornadaDTO jornadaToDTO(Jornada jornada) {
	        JornadaDTO dto = new JornadaDTO();
	        dto.setAsignadorPlanta(jornada.getAsignadorPlanta());
	        dto.setPlantaAsignada(jornada.getPlantaAsignada());
	        dto.setContenedores(jornada.getContenedores());
	        dto.setNumContenedores(jornada.getNumContenedores());
	        dto.setTotalCapacidad(jornada.getTotalCapacidad());
	        dto.setFechaJornada(jornada.getFechaJornada());

	        return dto;
	    }
	
}