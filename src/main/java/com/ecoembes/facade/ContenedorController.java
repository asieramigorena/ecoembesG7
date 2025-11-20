package com.ecoembes.facade;

import java.time.LocalDate;
import java.util.ArrayList;

import com.ecoembes.excepciones.Excepciones;
import com.ecoembes.service.EmpleadoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecoembes.dto.ContenedorDTO;
import com.ecoembes.service.ContenedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/ecoembes/contenedor")
public class ContenedorController {
	
	private final ContenedorService contenedorService;
	public ContenedorController(ContenedorService ContenedorService) {
		this.contenedorService = ContenedorService;
	}
	
	@Operation(summary = "Crear un nuevo contenedor")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "201", description = "Contenedor creado correctamente"),
	    @ApiResponse(responseCode = "400", description = "Error en la creacion del contenedor"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
	})
	@PostMapping("/{ubicacion}/{codPostal}/{CapacidadMax}")
	public ResponseEntity<?> crearContenedor(
			@RequestParam ("Ubicacion") String ubicacion,
	        @RequestParam ("Codigo Postal") int codPostal,
	        @RequestParam ("Capacidad Maxima") double capMaxima) {
        try{
            EmpleadoService.isLogin();
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
	    contenedorService.crearContenedor(ubicacion, codPostal, capMaxima);

	    ContenedorDTO respuesta = new ContenedorDTO (ubicacion, codPostal, capMaxima);
	    return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
	}
	
	@Operation(summary = "Actualizar el estado de los contenedores")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "201", description = "Contenedores actualizados correctamente"),
	    @ApiResponse(responseCode = "400", description = "Error en la actualizacion de los contenedores"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
	})
	@PutMapping("")
    public ResponseEntity<?> actualizarContenedores() {
        try{
            EmpleadoService.isLogin();
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        contenedorService.actualizarContenedores();
        return ResponseEntity.ok().build();
    }

	
	@Operation(summary = "Buscar contenedores por zona")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "201", description = "Contenedores obtenidos correctamente"),
	    @ApiResponse(responseCode = "400", description = "Error en la obtencion de los contenedores"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
	})
	@GetMapping("/{codPostal}")
    public ResponseEntity<?> getContsPorZona(@RequestParam("codPostal") int codPostal) {
        try {
            EmpleadoService.isLogin();
            ArrayList<ContenedorDTO> lista = contenedorService.getContsPorZona(codPostal);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Excepciones.SesionNoIniciadaException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
	
	
	@Operation(summary = "Buscar contenedores por fecha")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "201", description = "Contenedores obtenidos correctamente"),
	    @ApiResponse(responseCode = "400", description = "Error en la obtención de los contenedores"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
	})
	@GetMapping("/{idContenedor}/{fechaInicial}/{fechaFinal}")
	public ResponseEntity<?> getContsPorFecha(
	        @RequestParam("idContenedor") int idContenedor,
	        @RequestParam("Fecha inicial (YYYY-MM-DD)") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
	        @RequestParam("Fecha final (YYYY-MM-DD)") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

	    try {
            EmpleadoService.isLogin();
	        ArrayList<ContenedorDTO> lista = contenedorService.getContsPorFecha(idContenedor, fechaInicio, fechaFin);
	        return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Excepciones.SesionNoIniciadaException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
	    } catch (Exception e) {
	        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	    }
	}
}
