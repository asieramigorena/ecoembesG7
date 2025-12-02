package com.ecoembes.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;

import com.ecoembes.entity.*;
import com.ecoembes.dto.CapacidadPlantasDTO;
import com.ecoembes.external.SocketEcoembes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecoembes.dto.JornadaDTO;
import com.ecoembes.entity.Historico_Contenedores;
import com.ecoembes.entity.Jornada;
import com.ecoembes.external.PlasSBServiceProxy;

@Service
public class JornadaService {

    @Autowired
    private SocketEcoembes socketEcoembes;

    private static ArrayList<Jornada> jornadas = new ArrayList<>();
    private static Historico_Contenedores historico;

    public JornadaService() {

        // Inicializa el objeto historico
        historico = new Historico_Contenedores();

        // --- EMPLEADOS BASE ---
        Empleado e1 = new Empleado("A123", "Ana Ruiz", "123");
        Empleado e2 = new Empleado("B234", "Luis Martínez", "321");
        Empleado e3 = new Empleado("C345", "Marta Gómez", "234");
        Empleado e4 = new Empleado("D456", "Jon Etxeberria", "456");

        // --- PLANTAS BASE ---
        PlantaReciclaje p1 = new PlantaReciclaje("Planta Zabalgarbi");
        PlantaReciclaje p2 = new PlantaReciclaje("Planta Basauri");

        // --- CREACIÓN DE JORNADAS ---
        Jornada j1 = new Jornada(e1, Arrays.asList(e2, e3), p1, 5000, LocalDate.of(2025, 2, 1));
        Jornada j2 = new Jornada(e1, Arrays.asList(e2, e4), p2, 3000, LocalDate.of(2025, 2, 2));
        Jornada j3 = new Jornada(e1, Arrays.asList(e3, e4), p1, 5000, LocalDate.of(2025, 2, 3));

        // Agregar jornadas a la lista estática
        jornadas.add(j1);
        jornadas.add(j2);
        jornadas.add(j3);

        // --- CONTENEDORES ---
        ArrayList<Contenedor> c = ContenedorService.getContenedores();

        // Inicializa historico con los contenedores
        historico.getLista().put(LocalDate.of(2025, 2, 1), new ArrayList<>(Arrays.asList(c.get(0), c.get(1), c.get(2))));
        historico.getLista().put(LocalDate.of(2025, 2, 2), new ArrayList<>(Arrays.asList(c.get(3), c.get(4))));
        historico.getLista().put(LocalDate.of(2025, 2, 3), new ArrayList<>(Arrays.asList(c.get(5), c.get(6))));
    }

    public static ArrayList<Jornada> getJornadas() {
        return jornadas;
    }

    public ArrayList<CapacidadPlantasDTO> capacidadPlantas(LocalDate fecha) {
        ArrayList<CapacidadPlantasDTO> capacidades = new ArrayList<>();
//		for (Jornada jornada : jornadas) {
//			if (jornada.getFechaJornada().equals(fecha)) {
//				capacidades.add(new capacidadPlantasDTO(jornada.getPlantaAsignada().getNombre(), jornada.getTotalCapacidad()) );
//			}
//		}
        String fechaString = fecha.toString();
        PlasSBServiceProxy plasSBServiceProxy = new PlasSBServiceProxy();
        try {
        	capacidades.add(plasSBServiceProxy.getCapacidadPlasSB(fechaString));
        } catch (Exception e) {
			
		}
        try {
        	capacidades.add(strToCapacidadPlantasDTO(socketEcoembes.enviarGet("capacidades/" + fechaString)));
        } catch (Exception e) {
        	
        }
        return capacidades;
    }


    public JornadaDTO asignarContenedores(Jornada jornada, int idContenedor) throws IOException {

        for (Contenedor cont : ContenedorService.getContenedores()) {
            if (cont.getIdContenedor() == idContenedor) {

                if (jornada.getTotalCapacidad() < cont.getNivelActualToneladas()) {
                    throw new IOException("No se puede asignar el contenedor. Capacidad total de la planta superada.");
                } else {
                    jornada.setTotalCapacidad(jornada.getTotalCapacidad() - cont.getNivelActualToneladas());
                    if (historico.getLista().containsKey(jornada.getFechaJornada())) {
                        historico.getLista().get(jornada.getFechaJornada()).add(cont);
                    } else {
                        historico.getLista().put(jornada.getFechaJornada(), new ArrayList<Contenedor>());
                        historico.getLista().get(jornada.getFechaJornada()).add(cont);
                    }

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

    public static Historico_Contenedores getHistoricoContenedores() {
        return historico;
    }

    public static JornadaDTO jornadaToDTO(Jornada jornada) {
        JornadaDTO dto = new JornadaDTO();
        dto.setAsignadorPlanta(jornada.getAsignadorPlanta());
        dto.setPlantaAsignada(jornada.getPlantaAsignada());
        dto.setTotalCapacidad(jornada.getTotalCapacidad());
        dto.setFechaJornada(jornada.getFechaJornada());

        return dto;
    }

    public CapacidadPlantasDTO strToCapacidadPlantasDTO(String str) {
        String[] separado = str.split(";");
        return new CapacidadPlantasDTO("ContSocket", Double.parseDouble(separado[1]) );
    }
}
