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
}

