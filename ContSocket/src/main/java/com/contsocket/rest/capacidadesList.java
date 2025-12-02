package com.contsocket.rest;

import com.contsocket.entity.Capacidad;
import com.contsocket.service.ICapacidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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