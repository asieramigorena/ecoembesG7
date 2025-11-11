package com.ecoembes.facade;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

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
	@PostMapping()
	public ResponseEntity<EmpleadoDTO> login(@RequestParam String correo, @RequestParam String contrasena) {
		try {
			empleadoService.login(correo, contrasena);
			return ResponseEntity.ok("Sesion iniciada correctamente");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
