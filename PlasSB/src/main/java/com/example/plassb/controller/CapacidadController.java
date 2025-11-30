package com.example.plassb.controller;

import com.example.plassb.model.entitys.Capacidad;
import com.example.plassb.model.service.CapacidadService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/capacidades")
public class CapacidadController {

    private final CapacidadService capacidadRepository;

    public CapacidadController(CapacidadService capacidadRepository) {
        this.capacidadRepository = capacidadRepository;
    }

    @GetMapping
    public List<Capacidad> getAll() {
        return capacidadRepository.getCapacidad();
    }

    @GetMapping("/{fecha}")
    public Capacidad getByFecha(@PathVariable String fecha) {
        LocalDate localDate = LocalDate.parse(fecha);
        return capacidadRepository.findById(localDate)
                .orElseThrow(() -> new RuntimeException("Capacidad no encontrada para la fecha " + fecha));
    }

    @PostMapping
    public Capacidad create(@RequestBody Capacidad capacidad) {
        if (capacidadRepository.existsById(capacidad.getId())) {
            throw new RuntimeException("Ya existe capacidad para la fecha " + capacidad.getId());
        }
        return capacidadRepository.save(capacidad);
    }

    @PutMapping("/{fecha}")
    public Capacidad update(@PathVariable String fecha, @RequestBody Capacidad updated) {
        LocalDate localDate = LocalDate.parse(fecha);
        Capacidad capacidad = capacidadRepository.findById(localDate)
                .orElseThrow(() -> new RuntimeException("Capacidad no encontrada para la fecha " + fecha));
        capacidad.setCapacidad(updated.getCapacidad());
        return capacidadRepository.save(capacidad);
    }

    @DeleteMapping("/{fecha}")
    public void delete(@PathVariable String fecha) {
        LocalDate localDate = LocalDate.parse(fecha);
        capacidadRepository.deleteById(localDate);
    }
}
