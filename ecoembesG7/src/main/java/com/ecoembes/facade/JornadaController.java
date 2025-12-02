package com.ecoembes.facade;

import com.ecoembes.excepciones.Excepciones;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.ecoembes.dto.JornadaDTO;
import com.ecoembes.dto.CapacidadPlantasDTO;
import com.ecoembes.service.EmpleadoService;
import com.ecoembes.service.JornadaService;
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
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/")
    public ResponseEntity<?> capacidadesPorFecha(@RequestParam("fecha") String fecha) {
        try {
            EmpleadoService.isLogin();
            LocalDate localDate = LocalDate.parse(fecha);
            List<CapacidadPlantasDTO> capacidades = jornadaService.capacidadPlantas(localDate);
            return new ResponseEntity<>(capacidades, HttpStatus.OK);
        } catch (java.time.format.DateTimeParseException e) {
            return new ResponseEntity<>("Formato de fecha inválido. Use YYYY-MM-DD", HttpStatus.BAD_REQUEST);
        } catch (Excepciones.SesionNoIniciadaException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Asignar un contenedor a una jornada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contenedor asignado correctamente"),
            @ApiResponse(responseCode = "400", description = "Capacidad total de la planta superada"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "404", description = "Jornada o contenedor no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{idJornada}/contenedores/{idContenedor}")
    public ResponseEntity<?> asignarContenedor(
            @RequestParam("idJornada") int idJornada,
            @RequestParam("idContenedor") int idContenedor) {

        try {
            EmpleadoService.isLogin();
            Jornada jornada = jornadaService.getJornadaById(idJornada);
            if (jornada == null) {
                return new ResponseEntity<>("Jornada no encontrada", HttpStatus.NOT_FOUND);
            }

            JornadaDTO dto = jornadaService.asignarContenedores(jornada, idContenedor);
            return new ResponseEntity<>(dto, HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Excepciones.SesionNoIniciadaException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}