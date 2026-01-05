package com.ecoembesclient.data;

import java.time.LocalDate;

public record Jornada(Empleado empleado, String planta, double totalCapacidad, LocalDate fechaJornada) {
}
