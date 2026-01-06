package com.ecoembesclient.web;

import com.ecoembesclient.data.Empleado;
import com.ecoembesclient.proxies.ecoembesProxy;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ecoembesController {

    private final ecoembesProxy ecoembesProxy;

    public ecoembesController(ecoembesProxy ecoembesProxy) {
        this.ecoembesProxy = ecoembesProxy;
    }

    /**
     * Muestra el formulario de login
     */
    @GetMapping("/")
    public String mostrarLogin(Model model) {
        model.addAttribute("empleado", new Empleado(null, null));
        return "login";
    }

    /**
     * Procesa el login
     */
    @PostMapping("/login")
    public String procesarLogin(
            @ModelAttribute("empleado") Empleado empleado,
            Model model,
            HttpServletRequest request) {

        try {
            Empleado empleadoLogado = ecoembesProxy.login(empleado);

            // Guardar usuario en sesión
            request.getSession(true).setAttribute("empleado", empleadoLogado);

            return "redirect:/home";

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    /**
     * Página principal tras login
     */
    @GetMapping("/home")
    public String home() {
        return "index";
    }
}

