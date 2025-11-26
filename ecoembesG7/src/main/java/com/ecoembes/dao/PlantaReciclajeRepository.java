package com.ecoembes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecoembes.entity.PlantaReciclaje;

import java.util.Optional;

@Repository
public interface PlantaReciclajeRepository extends JpaRepository<PlantaReciclaje, Long> {
    Optional<PlantaReciclaje> findByNombre(String nombre);

}
