package com.ecoembes.service;

import java.util.ArrayList;
import java.util.List;

import com.ecoembes.dao.EmpleadoDAO;
import com.ecoembes.dao.JornadaDAO;
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

        Iterable<Empleado> iterable = empleadoDAO.findAll();
        List<Empleado> empleados = new ArrayList<>();
        iterable.forEach(empleados::add);

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

        Iterable<Empleado> iterable = empleadoDAO.findAll();
        List<Empleado> empleados = new ArrayList<>();
        iterable.forEach(empleados::add);

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