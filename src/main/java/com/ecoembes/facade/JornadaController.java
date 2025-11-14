package com.ecoembes.facade;

import com.ecoembes.excepciones.EmpleadoExcepciones;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.ecoembes.dto.EmpleadoDTO;
import com.ecoembes.dto.capacidadPlantasDTO;
import com.ecoembes.service.EmpleadoService;
import com.ecoembes.service.JornadaService;
import com.ecoembes.entity.Empleado;
import com.ecoembes.entity.Jornada;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/ecoembes/jornada")
public class JornadaController {
    private final JornadaService jornadaService;

    public JornadaController(JornadaService jornadaService, EmpleadoService empleadoService) {
        this.jornadaService = jornadaService;
    }

    @Operation(summary = "Consulta capacidades de plantas para una fecha")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Capacidades obtenidas correctamente"),
            @ApiResponse(responseCode = "400", description = "Formato de fecha inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/capacidades")
    public ResponseEntity<?> capacidadesPorFecha(@RequestParam("fecha") String fecha) {
        try {
            LocalDate localDate = LocalDate.parse(fecha);
            List<capacidadPlantasDTO> capacidades = jornadaService.capacidadPlantas(localDate);
            return new ResponseEntity<>(capacidades, HttpStatus.OK);
        } catch (java.time.format.DateTimeParseException e) {
            return new ResponseEntity<>("Formato de fecha inválido. Use YYYY-MM-DD", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Asignar contenedor a una jornada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contenedor asignado correctamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos / jornada no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/asignar")
    public ResponseEntity<?> asignarContenedor(
            @RequestParam("idJornada") int idJornada,
            @RequestParam("ubicacion") String ubicacion,
            @RequestParam("codPostal") int codPostal,
            @RequestParam("nivel") double nivelLlenadoContenedor,
            @RequestParam("capacidadMax") double capacidadMax,
            @RequestParam(value = "asignadorCorreo", required = false) String asignadorCorreo
    ) {
        try {
            Jornada jornada = null;
            for (Jornada j : JornadaService.getJornadas()) {
                if (j.getIdJornada() == idJornada) {
                    jornada = j;
                    break;
                }
            }
            if (jornada == null) {
                return new ResponseEntity<>("Jornada no encontrada para id: " + idJornada, HttpStatus.BAD_REQUEST);
            }
			// Si se proporciona un correo de asignador, buscar el empleado y asignarlo
            if (asignadorCorreo != null && !asignadorCorreo.isBlank()) {
                Empleado asignador = null;
                for (Empleado e : EmpleadoService.getEmpleados()) {
                    if (e.getCorreo().equalsIgnoreCase(asignadorCorreo)) {
                        asignador = e;
                        break;
                    }
                }
                if (asignador == null) {
                    return new ResponseEntity<>("Empleado asignador no encontrado: " + asignadorCorreo, HttpStatus.BAD_REQUEST);
                }
                jornada.setAsignadorPlanta(asignador);
            }

            // Delegar la asignación del contenedor al servicio de jornada
            jornadaService.asignarContenedores(jornada, ubicacion, codPostal, capacidadMax, nivelLlenadoContenedor);


            return new ResponseEntity<>("Contenedor asignado correctamente", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}