package com.ecoembes.service;

import java.util.ArrayList;
import java.util.List;

import com.ecoembes.dao.EmpleadoDAO;
import com.ecoembes.excepciones.Excepciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecoembes.dto.EmpleadoDTO;
import com.ecoembes.entity.Empleado;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoDAO empleadoDAO;

    private static Empleado activo;


    public EmpleadoDTO login(String correo, String contrasena) throws Exception {
        if (correo.isBlank() || contrasena.isBlank()) {
            throw new Excepciones.CredencialesInvalidasException("Credenciales invalidas");
        }

        // Verificar si ya hay un usuario activo (solo puede haber uno a la vez)
        if (activo != null && activo.getToken() != null) {
            throw new Excepciones.ErrorTokenException("Ya hay un usuario con sesion iniciada: " + activo.getCorreo());
        }

        Iterable<Empleado> iterable = empleadoDAO.findAll();
        List<Empleado> empleados = new ArrayList<>();
        iterable.forEach(empleados::add);

        for (Empleado empleado : empleados) {
            if (empleado.getCorreo().equals(correo)) {
                if (empleado.getContrasena().equals(contrasena)) {
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
		if (activo == null) {
			throw new Excepciones.ErrorTokenException("No hay ninguna sesion iniciada");
		}

		if (!activo.getCorreo().equals(correo)) {
			throw new Excepciones.ErrorTokenException("No hay ninguna sesion iniciada para el usuario: " + correo);
		}

		activo.setTokenNull();
		activo = null;
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
