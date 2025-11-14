package com.ecoembes.facade;

import com.ecoembes.excepciones.EmpleadoExcepciones;
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
@RequestMapping("/ecoembes")
public class EmpeladoController {
	private final EmpleadoService empleadoService;
	public EmpeladoController(EmpleadoService empleadoService) {
		this.empleadoService = empleadoService;
	}
	
	@Operation(summary = "Iniciar sesion de un empleado")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sesion iniciada correctamente"),
            @ApiResponse(responseCode = "400", description = "Campos vacios"),
            @ApiResponse(responseCode = "401", description = "Credenciales invalidas"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado"),
            @ApiResponse(responseCode = "409", description = "Error de token"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")

	})
	@PostMapping("/correo/contraseña")
	public ResponseEntity<?> login(@RequestParam("Correo") String correo, @RequestParam("Contraseña") String contrasena) {
		try {
			EmpleadoDTO actual = empleadoService.login(correo, contrasena);
			return new ResponseEntity<>(actual, HttpStatus.OK);
		} catch (EmpleadoExcepciones.EmpleadoNoEncontradoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (EmpleadoExcepciones.CredencialesInvalidasException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EmpleadoExcepciones.ErrorTokenException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	@Operation(summary = "Cerrar sesion de un empleado")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sesion cerrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en el cierre de sesion"),
            @ApiResponse(responseCode = "409", description = "Error de token"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
	})
	@PostMapping("/correo")
	public ResponseEntity<?> logout(@RequestParam("Correo") String correo) {
		try {
			empleadoService.logout(correo);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (EmpleadoExcepciones.ErrorTokenException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (EmpleadoExcepciones.EmpleadoNoEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
}
