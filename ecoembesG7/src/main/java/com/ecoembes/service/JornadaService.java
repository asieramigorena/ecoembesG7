package com.ecoembes.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;

import com.ecoembes.dao.ContenedorDAO;
import com.ecoembes.dao.Historico_ContenedoresDAO;
import com.ecoembes.dao.JornadaDAO;
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

    @Autowired
    private JornadaDAO jornadaDAO;

    @Autowired
    private ContenedorDAO contenedorDAO;

    @Autowired
    private Historico_ContenedoresDAO historicoDAO;

    public Jornada crearJornada(LocalDate fecha, Empleado jefe, ArrayList<Empleado> personal, PlantaReciclaje planta, double totalCapacidad) {
        Jornada j = new Jornada();
        j.setFechaJornada(fecha);
        j.setAsignadorPlanta(jefe);
        j.setPlantaAsignada(planta);
        j.setTotalCapacidad(totalCapacidad);
        j.setPersonal(personal);

        return jornadaDAO.save(j); // Guarda en la BD
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
			System.err.println(e.getMessage());
		}
        try {
        	capacidades.add(strToCapacidadPlantasDTO(socketEcoembes.enviarGet("capacidad/" + fechaString)));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return capacidades;
    }


    public JornadaDTO asignarContenedores(Jornada jornada, int idContenedor) throws IOException {

        Iterable<Contenedor> iterable = contenedorDAO.findAll();
        List<Contenedor> contenedores = new ArrayList<>();
        iterable.forEach(contenedores::add);

        Iterable<Historico_Contenedores> iterable2 = historicoDAO.findAll();
        List<Historico_Contenedores> historico = new ArrayList<>();
        iterable2.forEach(historico::add);

        for (Contenedor cont : contenedores) {
            if (cont.getIdContenedor() == idContenedor) {

                if (jornada.getTotalCapacidad() < cont.getNivelActualToneladas()) {
                    throw new IOException("No se puede asignar el contenedor. Capacidad total de la planta superada.");
                } else {
                    jornada.setTotalCapacidad(jornada.getTotalCapacidad() - cont.getNivelActualToneladas());
                    jornadaDAO.save(jornada);
                    if(historicoDAO.existsById(jornada.getFechaJornada())) {

                        for (Historico_Contenedores hc : historico) {
                            if (hc.getFecha() == jornada.getFechaJornada()) {
                                hc.getContenedores().add(cont);
                                historicoDAO.save(hc);
                            }
                        }
                    }else{
                        Historico_Contenedores hc = new Historico_Contenedores();
                        ArrayList<Contenedor> c= new ArrayList<>();
                        hc.setFecha(jornada.getFechaJornada());
                        hc.setContenedores(c);
                        historicoDAO.save(hc);
                    }
                }
            }
        }
        socketEcoembes.enviarPut("capacidades/", jornada.getFechaJornada().toString() + "/" + jornada.getTotalCapacidad());
        return jornadaToDTO(jornada);
    }

    public Jornada getJornadaById(int id) {

        Iterable<Jornada> iterable = jornadaDAO.findAll();
        List<Jornada> jornadas = new ArrayList<>();
        iterable.forEach(jornadas::add);

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
        dto.setTotalCapacidad(jornada.getTotalCapacidad());
        dto.setFechaJornada(jornada.getFechaJornada());

        return dto;
    }

    public CapacidadPlantasDTO strToCapacidadPlantasDTO(String str) {
        String[] separado = str.split(";");
        return new CapacidadPlantasDTO("ContSocket", Double.parseDouble(separado[1]) );
    }
}
