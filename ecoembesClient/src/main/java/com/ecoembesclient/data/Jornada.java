package com.ecoembesclient.data;

import java.time.LocalDate;

public record Jornada(String correoAsignador, String nombrePlanta, double totalCapacidad, LocalDate fechaJornada) {
}
