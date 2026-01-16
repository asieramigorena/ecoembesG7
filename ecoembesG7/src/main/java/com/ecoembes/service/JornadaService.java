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
import com.ecoembes.external.GatewayFactory;
import com.ecoembes.external.PlasSBServiceGateway;
import com.ecoembes.external.SocketEcoembes;
import com.ecoembes.external.Gateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecoembes.dto.JornadaDTO;
import com.ecoembes.entity.Historico_Contenedores;
import com.ecoembes.entity.Jornada;

@Service
public class JornadaService {

    @Autowired
    private SocketEcoembes socketEcoembes;

    @Autowired
    private PlasSBServiceGateway plasSBServiceGateway;

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
        Gateway gPlas = GatewayFactory.getGateway("PlasSB");
        try {
            PlasSBServiceGateway plas = (gPlas instanceof PlasSBServiceGateway) ? (PlasSBServiceGateway) gPlas : plasSBServiceGateway;
            capacidades.add(plas.getCapacidadPlasSB(fechaString));
        } catch (Exception e) {
			System.err.println(e.getMessage());
		}
        Gateway gSocket = GatewayFactory.getGateway("SocketEcoembes");
        try {
            SocketEcoembes socket = (gSocket instanceof SocketEcoembes) ? (SocketEcoembes) gSocket : socketEcoembes;
            String respuesta = socket.enviar("capacidad/" + fechaString);
            capacidades.add(strToCapacidadPlantasDTO(respuesta));
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
//            	if (jornada.getPlantaAsignada().getNombre().equals("PlasSB")) {
//            		PlasSBServiceProxy plas = new PlasSBServiceProxy();
//            		try {
//            			if (cont.getNivelActualToneladas() < plas.getCapacidad(jornada.getFechaJornada().toString()).getCapacidadTotal()) {
//            				jornada.getC.add(cont); 	
//            				plas.updateCapacidad(jornada.getFechaJornada().toString(), plas.getCapacidad(jornada.getFechaJornada().toString()).getCapacidadTotal()-cont.getNivelActualToneladas());
//            			}
//            		} catch(Exception e) {
//            			throw new IOException("No se puedo asignar el contenedor a PlasSB. ");
//            		}
//            			
//            	}
            	
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
                        List<Contenedor> c = new ArrayList<>();
                        Historico_Contenedores hc = new Historico_Contenedores(jornada.getFechaJornada(), c);
                        hc.getContenedores().add(cont);
                        historicoDAO.save(hc);
                    }
                }
            }
        }
        Gateway gSocket = GatewayFactory.getGateway("SocketEcoembes");
        try {
            SocketEcoembes socket = (gSocket instanceof SocketEcoembes) ? (SocketEcoembes) gSocket : socketEcoembes;
            // enviar PUT con formato simple: "PUT capacidades/<fecha>/<capacidad>"
            String bodyMessage = "PUT capacidades/" + jornada.getFechaJornada().toString() + "/" + jornada.getTotalCapacidad();
            System.out.println(socket.enviar(bodyMessage));
        } catch (Exception e) {
            System.err.println("Error enviando actualización por socket: " + e.getMessage());
        }

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
        dto.setCorreoAsignador(jornada.getAsignadorPlanta().getCorreo());
        dto.setNombrePlanta(jornada.getPlantaAsignada().getNombre());
        dto.setTotalCapacidad(jornada.getTotalCapacidad());
        dto.setFechaJornada(jornada.getFechaJornada());

        return dto;
    }

    public CapacidadPlantasDTO strToCapacidadPlantasDTO(String str) {
        String[] separado = str.split(";");
        return new CapacidadPlantasDTO("ContSocket", Double.parseDouble(separado[1]) );
    }
}
