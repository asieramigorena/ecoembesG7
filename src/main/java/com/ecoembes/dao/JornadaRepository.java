package com.ecoembes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecoembes.entity.Jornada;

import java.util.Optional;

public interface JornadaRepository extends JpaRepository<Jornada, Long> {
	Optional<Jornada> findByTipo(String tipo);

}
