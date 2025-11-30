package com.example.plassb.model.service;

import com.example.plassb.model.entitys.Capacidad;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ICapacidadService {
    List<Capacidad> getCapacidad();
    Optional<Capacidad> findById(LocalDate fecha);
    Capacidad save(Capacidad capacidad);
    boolean existsById(LocalDate fecha);
    void deleteById(LocalDate fecha);
}