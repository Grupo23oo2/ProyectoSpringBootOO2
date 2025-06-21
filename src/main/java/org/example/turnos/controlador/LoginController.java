package org.example.turnos.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String mostrarLogin(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

        if (error != null) {
            model.addAttribute("mensajeError", "Usuario o contraseña incorrectos");
        }

        if (logout != null) {
            model.addAttribute("mensajeLogout", "Sesión cerrada exitosamente");
        }

        return "login"; // HTML login.html en templates/
    }
}