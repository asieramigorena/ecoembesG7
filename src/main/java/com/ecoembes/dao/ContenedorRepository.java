package com.ecoembes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.ecoembes.entity.Contenedor;

@Repository
public interface ContenedorRepository extends JpaRepository<Contenedor, Long> {
	Optional<Contenedor> findByCodigo(int codigo);

}
