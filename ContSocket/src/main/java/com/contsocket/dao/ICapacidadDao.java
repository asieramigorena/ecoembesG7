package com.contsocket.dao;

import com.contsocket.entity.Capacidad;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface ICapacidadDao extends CrudRepository<Capacidad, LocalDate> {
}