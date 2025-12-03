package com.ecoembes.dao;

import com.ecoembes.entity.Empleado;
import org.springframework.data.repository.CrudRepository;

public interface EmpleadoDAO extends CrudRepository<Empleado, Integer> {
}
