package com.example.plassb.model.dao;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.plassb.model.entitys.Capacidad;

public interface ICapacidadDao extends CrudRepository<Capacidad, LocalDate> {
}