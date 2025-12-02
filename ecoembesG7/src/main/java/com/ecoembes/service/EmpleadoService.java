package com.ecoembes.service;

import java.util.ArrayList;

import com.ecoembes.excepciones.Excepciones;
import org.springframework.stereotype.Service;

import com.ecoembes.dto.EmpleadoDTO;
import com.ecoembes.entity.Empleado;

@Service
public class EmpleadoService {
	private static ArrayList<Empleado> empleados = new ArrayList<>();
    private static Empleado activo;
	
	public EmpleadoService() {
		empleados.add(new Empleado("Jose Maria", "josemari@correo.com", "contrasena1"));
		empleados.add(new Empleado("Ana Lopez", "analope@correo.com", "contrasena2"));
	}

	public static ArrayList<Empleado> getEmpleados() {
		return empleados;
	}

    public EmpleadoDTO login(String correo, String contrasena) throws Exception {
        if (correo.isBlank() || contrasena.isBlank()) {
            throw new Excepciones.CredencialesInvalidasException("Credenciales invalidas");
        }
        for (Empleado empleado : empleados) {
            if (empleado.getCorreo().equals(correo)) {
                if (empleado.getToken() != null) {
                    throw new Excepciones.ErrorTokenException("El usuario " + empleado.getCorreo() + " ya tiene una sesion iniciada");
                }
                else if (empleado.getContrasena().equals(contrasena)) {
                    empleado.setToken();
                    activo = empleado;
                    return empleadoToDTO(empleado);
                }
                break;
            }
        }
        throw new Excepciones.CredencialesInvalidasException("Credenciales invalidas");
    }
	
	
	public void logout(String correo) throws Exception {
		for (Empleado empleado : empleados) {
			if (empleado.getCorreo().equals(correo)) {
				if (empleado.getToken() != null) {
					empleado.setTokenNull();
                    activo = null;
					return;
				} else {
					throw new Excepciones.ErrorTokenException("No hay ninguna sesion iniciada para el usuario: " + correo);
				}
			}
		}
		throw new Excepciones.EmpleadoNoEncontradoException("No se ha encontrado el empleado con correo: " + correo);
	}

    public static void isLogin() throws Exception {
        if (activo == null || activo.getToken() == null) {
            throw new Excepciones.SesionNoIniciadaException("No hay ninguna sesion iniciada");
        }
    }

	private EmpleadoDTO empleadoToDTO (Empleado empleado) {
		return new EmpleadoDTO(empleado.getCorreo());
	}
}