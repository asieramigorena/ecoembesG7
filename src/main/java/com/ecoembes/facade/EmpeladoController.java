package com.ecoembes.facade;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.ecoembes.dto.EmpleadoDTO;
import com.ecoembes.service.EmpleadoService;

@RestController
@RequestMapping("/empleados")
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
}
