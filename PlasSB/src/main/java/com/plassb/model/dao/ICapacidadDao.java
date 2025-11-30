package com.plassb.model.dao;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;

import com.plassb.model.entitys.Capacidad;

public interface ICapacidadDao extends CrudRepository<Capacidad, LocalDate> {
}