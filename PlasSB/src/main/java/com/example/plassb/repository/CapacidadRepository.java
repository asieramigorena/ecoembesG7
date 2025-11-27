package com.example.plassb.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.plassb.model.Capacidad;

public interface CapacidadRepository extends JpaRepository<Capacidad, LocalDate> {
}