package com.plassb.model.service;

import com.plassb.model.dao.ICapacidadDao;
import com.plassb.model.entitys.Capacidad;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CapacidadService implements ICapacidadService {

    private final ICapacidadDao capacidadDao;

    public CapacidadService(ICapacidadDao capacidadDao) {
        this.capacidadDao = capacidadDao;
    }

    @Override
    public List<Capacidad> getCapacidad() {
        return (List<Capacidad>) capacidadDao.findAll();
    }

    @Override
    public Optional<Capacidad> findById(LocalDate fecha) {
        return capacidadDao.findById(fecha);
    }

    @Override
    public Capacidad save(Capacidad capacidad) {
        return capacidadDao.save(capacidad);
    }

    @Override
    public boolean existsById(LocalDate fecha) {
        return capacidadDao.existsById(fecha);
    }

    @Override
    public void deleteById(LocalDate fecha) {
        capacidadDao.deleteById(fecha);
    }
}
