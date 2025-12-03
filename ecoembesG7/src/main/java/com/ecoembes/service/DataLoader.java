package com.ecoembes.service;

import com.ecoembes.entity.*;
import com.ecoembes.dao.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private EmpleadoDAO empleadoDAO;
    @Autowired
    private PlantaReciclajeDAO plantaDAO;
    @Autowired
    private ContenedorDAO contenedorDAO;
    @Autowired
    private JornadaDAO jornadaDAO;
    @Autowired
    private Historico_ContenedoresDAO historicoDAO;

    @Override
    public void run(String... args) throws Exception {

        // --- Empleados ---
        Empleado jefe = new Empleado();
        jefe.setNombre("Juan Pérez");
        jefe.setCorreo("juan@empresa.com");
        jefe.setContrasena("1234");
        empleadoDAO.save(jefe);

        Empleado e1 = new Empleado();
        e1.setNombre("Ana López");
        e1.setCorreo("ana@empresa.com");
        e1.setContrasena("abcd");
        empleadoDAO.save(e1);

        Empleado e2 = new Empleado();
        e2.setNombre("Carlos Ruiz");
        e2.setCorreo("carlos@empresa.com");
            e2.setContrasena("xyz");
        empleadoDAO.save(e2);

        // --- Plantas de reciclaje ---
        PlantaReciclaje planta1 = new PlantaReciclaje();
        planta1.setNombre("PlasSB .Ltd");
        plantaDAO.save(planta1);

        PlantaReciclaje planta2 = new PlantaReciclaje();
        planta2.setNombre("ContSocket .Ltd");
        plantaDAO.save(planta2);

        // --- Contenedores ---
        Contenedor c1 = new Contenedor();
        c1.setUbicacion("Nave 1");
        c1.setCodPostal(28001);
        c1.setCapMaxima(100);
        c1.setNivelActual(nivelLenado.VERDE);
        c1.setNumEnvases(50);
        c1.setNivelActualToneladas(10.0);
        contenedorDAO.save(c1);

        Contenedor c2 = new Contenedor();
        c2.setUbicacion("Nave 2");
        c2.setCodPostal(28002);
        c2.setCapMaxima(150);
        c2.setNivelActual(nivelLenado.NARANJA);
        c2.setNumEnvases(75);
        c2.setNivelActualToneladas(20.0);
        contenedorDAO.save(c2);

        Contenedor c3 = new Contenedor();
        c3.setUbicacion("Nave 3");
        c3.setCodPostal(28003);
        c3.setCapMaxima(200);
        c3.setNivelActual(nivelLenado.ROJO);
        c3.setNumEnvases(100);
        c3.setNivelActualToneladas(30.0);
        contenedorDAO.save(c3);

        // --- Historico Contenedores ---
        Historico_Contenedores hist1 = new Historico_Contenedores();
        hist1.setFecha(LocalDate.of(2025, 12, 1));
        hist1.setContenedores(new ArrayList<>());
        hist1.getContenedores().add(c1);
        hist1.getContenedores().add(c2);
        historicoDAO.save(hist1);

        Historico_Contenedores hist2 = new Historico_Contenedores();
        hist2.setFecha(LocalDate.of(2025, 12, 2));
        hist2.setContenedores(new ArrayList<>());
        hist2.getContenedores().add(c3);
        historicoDAO.save(hist2);

        // --- Jornadas ---
        Jornada j1 = new Jornada();
        j1.setFechaJornada(LocalDate.of(2025, 12, 3));
        j1.setAsignadorPlanta(jefe);
        j1.setPlantaAsignada(planta1);
        j1.setTotalCapacidad(500);
        j1.setPersonal(new ArrayList<>());
        j1.getPersonal().add(e1);
        j1.getPersonal().add(e2);
        jornadaDAO.save(j1);

        Jornada j2 = new Jornada();
        j2.setFechaJornada(LocalDate.of(2025, 12, 4));
        j2.setAsignadorPlanta(jefe);
        j2.setPlantaAsignada(planta2);
        j2.setTotalCapacidad(400);
        j2.setPersonal(new ArrayList<>());
        j2.getPersonal().add(e2);

        jornadaDAO.save(j2);

        System.out.println("Datos de ejemplo cargados correctamente!");
    }
}

