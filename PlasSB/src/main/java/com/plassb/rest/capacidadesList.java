package com.plassb.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plassb.model.entitys.Capacidad;
import com.plassb.model.service.ICapacidadService;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping (value="/api/")
public class capacidadesList {
	
	@Autowired
	private ICapacidadService capacidadService;

	@GetMapping(value="capacidades")
	public List<Capacidad> getTasks() {
		return capacidadService.getCapacidad();
	}
	
	
	
}