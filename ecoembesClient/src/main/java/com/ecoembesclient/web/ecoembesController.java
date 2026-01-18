package com.ecoembesclient.web;

import com.ecoembesclient.data.CapacidadPlantas;
import com.ecoembesclient.data.Contenedor;
import com.ecoembesclient.data.Empleado;
import com.ecoembesclient.data.Jornada;
import com.ecoembesclient.proxies.ecoembesProxy;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ecoembesController {

    private final ecoembesProxy ecoembesProxy;

    public ecoembesController(ecoembesProxy ecoembesProxy) {
        this.ecoembesProxy = ecoembesProxy;
    }

    @GetMapping({"/", "/login"})
    public String mostrarLogin(Model model) {

        if (!model.containsAttribute("empleado")) {
            model.addAttribute("empleado", new Empleado(null, null));
        }
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(
            @ModelAttribute("empleado") Empleado empleado,
            Model model,
            HttpServletRequest request) {

        try {
            Empleado empleadoLogado = ecoembesProxy.login(empleado);
            request.getSession(true).setAttribute("empleado", empleadoLogado);
            return "redirect:/home";

        } catch (RuntimeException e) {
            model.addAttribute("error", "Credenciales incorrectas: " + e.getMessage());
            // Es importante que el objeto empleado vuelva a la vista para no perder lo escrito
            model.addAttribute("empleado", empleado);
            return "login";
        }
    }

    @GetMapping("/home")
    public String home(HttpServletRequest request) {
        // Protección básica: si no hay sesión, al login
        if (request.getSession().getAttribute("empleado") == null) {
            return "redirect:/login";
        }
        return "index";
    }
/*
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        Empleado empleado = (Empleado) request.getSession().getAttribute("empleado");
        if (empleado != null) {
            try {
                ecoembesProxy.logout(empleado);
            } catch (Exception e) {
                // Ignorar errores de logout
            }
        }
        request.getSession().invalidate();
        return "redirect:/login";
    }

 */
    // Mostrar formulario de logout
    @GetMapping({"/logout"})
    public String mostrarLogout(Model model) {

        if (!model.containsAttribute("empleado")) {
            model.addAttribute("empleado", new Empleado(null, null));
        }
        return "logout";
    }

    @PostMapping("/logout")
    public String procesarLogout(
            @RequestParam("correo") String correo,
            Model model,
            HttpServletRequest request) {

        try {
            if (correo == null || correo.isBlank()) {
                throw new RuntimeException("Debe introducir su correo para cerrar sesión");
            }

            // Llamamos al proxy que hace POST al backend
            ecoembesProxy.logout(correo);

            // Invalidamos la sesión local
            request.getSession().invalidate();

            return "redirect:/login";

        } catch (RuntimeException e) {
            model.addAttribute("error", "Error cerrando sesión: " + e.getMessage());
            model.addAttribute("correo", correo);
            return "logout"; // muestra el mismo formulario
        }
    }




    @GetMapping("/jornada/asignacion")
    public String mostrarAsignacion(HttpServletRequest request) {
        if (request.getSession().getAttribute("empleado") == null) {
            return "redirect:/login";
        }
        return "asignar";
    }

    @PostMapping("/jornada/asignacion")
    public String asignarContenedor(
            @RequestParam("idJornada") int idJornada,
            @RequestParam("idContenedor") int idContenedor,
            Model model,
            HttpServletRequest request) {

        if (request.getSession().getAttribute("empleado") == null) {
            return "redirect:/login";
        }

        try {
            Jornada jornadaActualizada = ecoembesProxy.asignarContenedor(idJornada, idContenedor);
            model.addAttribute("mensaje", "Contenedor asignado correctamente a la jornada");
            model.addAttribute("jornada", jornadaActualizada);
            return "asignar";

        } catch (RuntimeException e) {
            model.addAttribute("error", "Error al asignar contenedor: " + e.getMessage());
            return "asignar";
        }
    }

    @GetMapping("/ecoembes/contenedor/fecha")
    public String mostrarContenedor(HttpServletRequest request) {
        if (request.getSession().getAttribute("empleado") == null) {
            return "redirect:/login";
        }
        return "buscar";
    }

    @PostMapping("/ecoembes/contenedor/fecha")
    public String buscarContenedor(
            @RequestParam("idContenedor") int idContenedor,
            //el formato de las fechas es yyyy-mm-dd
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            Model model,
            HttpServletRequest request) {

        if (request.getSession().getAttribute("empleado") == null) {
            return "redirect:/login";
        }

        try {
            List<Contenedor> contenedores = ecoembesProxy.buscarContenedoresPorFecha(idContenedor, fechaInicio, fechaFin);
            model.addAttribute("contenedores", contenedores);
            model.addAttribute("mensaje", "Contenedores encontrados correctamente.");
            return "buscar";

        } catch (RuntimeException e) {
            model.addAttribute("error", "Error al buscar contenedores: " + e.getMessage());
            return "buscar";
        }
    }
    @GetMapping("/ecoembes/jornada/capacidades")
    public String mostrarCapacidadesPorFecha(HttpServletRequest request) {
        if (request.getSession().getAttribute("empleado") == null) {
            return "redirect:/login";
        }
        return "buscarCap";
    }

    @PostMapping("/ecoembes/jornada/capacidades")
    public String capacidadesPorFecha(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            Model model,
            HttpServletRequest request) {

        if (request.getSession().getAttribute("empleado") == null) {
            return "redirect:/login";
        }

        try {
            List<CapacidadPlantas> capacidades = ecoembesProxy.capacidadesPorFecha(fecha);
            model.addAttribute("capacidades", capacidades);
            model.addAttribute("mensaje", "Capacidades obtenida correctamente.");
            return "buscarCap";

        } catch (RuntimeException e) {
            model.addAttribute("error", "Error al buscar las Capacidades: " + e.getMessage());
            return "buscarCap";
        }
    }

    @GetMapping("/ecoembes/contenedor/zona")
    public String mostrarContenedorZona(HttpServletRequest request) {
        if (request.getSession().getAttribute("empleado") == null) {
            return "redirect:/login";
        }
        return "buscarZona";
    }

    @PostMapping("/ecoembes/contenedor/zona")
    public String buscarContenedorZona(
            @RequestParam("codPostal") int zona,
            Model model,
            HttpServletRequest request) {

        if (request.getSession().getAttribute("empleado") == null) {
            return "redirect:/login";
        }

        try {
            List<Contenedor> contenedores = ecoembesProxy.buscarContenedoresPorZona( zona);
            model.addAttribute("contenedores", contenedores);
            model.addAttribute("mensaje", "Contenedores encontrados correctamente.");
            return "buscarZona";

        } catch (RuntimeException e) {
            model.addAttribute("error", "Error al buscar contenedores: " + e.getMessage());
            return "buscarZona";
        }
    }
}

