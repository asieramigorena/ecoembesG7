package com.ecoembes.dao;

import com.ecoembes.entity.Historico_Contenedores;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface Historico_ContenedoresDAO extends CrudRepository<Historico_Contenedores, LocalDate> {
}
