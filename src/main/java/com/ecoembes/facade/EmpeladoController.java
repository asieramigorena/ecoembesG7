package com.ecoembes.facade;

import java.util.ArrayList;

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

import com.ecoembes.dto.ContenedorDTO;
import com.ecoembes.dto.EmpleadoDTO;
import com.ecoembes.entity.Contenedor;
import com.ecoembes.service.ContenedorService;
import com.ecoembes.service.EmpleadoService;

@RestController
@RequestMapping("/ecoembes")
public class EmpeladoController {
	private final EmpleadoService empleadoService;
	public EmpeladoController(EmpleadoService empleadoService) {
		this.empleadoService = empleadoService;
	}
	
	@Operation(summary = "Iniciar sesion de un empleado")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Sesion iniciada correctamente"),
	    @ApiResponse(responseCode = "400", description = "Error en el inicio de sesion")
	})
	@PostMapping("/login")
	public ResponseEntity<EmpleadoDTO> login(@RequestParam("Correo") String correo, @RequestParam("Contraseña") String contrasena) {
		try {
			EmpleadoDTO actual = empleadoService.login(correo, contrasena);
			return new ResponseEntity<>(actual, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@Operation(summary = "Cerrar sesion de un empleado")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Sesion cerrada correctamente"),
	    @ApiResponse(responseCode = "400", description = "Error en el cierre de sesion")
	})
	@PostMapping("/logout")
	public ResponseEntity<Void> logout(@RequestParam("Correo") String correo) {
		if (correo.isBlank() || correo == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			empleadoService.logout(correo);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	 @PostMapping("/crear")
	 public ResponseEntity<ContenedorDTO> crearContenedor(
	        @RequestParam ("Ubicacion") String ubicacion,
	        @RequestParam ("Codigo Postal") int codPostal,
	        @RequestParam ("Capacidad Maxima") double capMaxima) {

	    EmpleadoService.crearContenedor(ubicacion, codPostal, capMaxima);

	    ContenedorDTO respuesta = new ContenedorDTO (ubicacion, codPostal, capMaxima);
	    return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
	}
	
	
    @PostMapping("/actualizar")
    public ResponseEntity<Void> actualizarContenedores() {
        ContenedorService.actualizarContenedores();
        return ResponseEntity.ok().build();
    }

	
	@Operation(summary = "Buscar contenedores por zona")
	
	@GetMapping("/zona")
    public ResponseEntity<ArrayList<ContenedorDTO>> getContsPorZona(@RequestParam("codPostal") int codPostal) {
        try {
            ArrayList<ContenedorDTO> lista = EmpleadoService.getContsPorZona(codPostal);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
