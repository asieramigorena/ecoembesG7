package com.contsocket.service;

import com.contsocket.entity.Capacidad;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Order(1)
public class DataInitializer implements CommandLineRunner {

    private final ICapacidadService capacidadService;

    public DataInitializer(ICapacidadService capacidadService) {
        this.capacidadService = capacidadService;
    }

    @Override
    public void run(String... args) {
        try {
            LocalDate d1 = LocalDate.of(2025, 12, 1);
            if (!capacidadService.existsById(d1)) {
                Capacidad c1 = new Capacidad();
                c1.setId(d1);
                c1.setCapacidad(100);
                capacidadService.save(c1);
            }

            LocalDate d2 = LocalDate.of(2025, 12, 2);
            if (!capacidadService.existsById(d2)) {
                Capacidad c2 = new Capacidad();
                c2.setId(d2);
                c2.setCapacidad(150);
                capacidadService.save(c2);
            }

            System.out.println("DataInitializer: datos de prueba insertados (si eran necesarios).");
        } catch (NoSuchMethodError e) {
            System.out.println("DataInitializer: la entidad Capacidad no tiene los setters esperados: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("DataInitializer: error al inicializar datos: " + e.getMessage());
        }
    }
}
