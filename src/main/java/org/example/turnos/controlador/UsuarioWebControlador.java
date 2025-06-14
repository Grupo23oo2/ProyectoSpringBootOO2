package org.example.turnos.controlador;

import org.example.turnos.dtos.RolUsuarioDTO;
import org.example.turnos.servicios.IUsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioWebControlador {

    @Autowired
    private IUsuarioServicio usuarioServicio;

    // Formulario para ingresar ID de usuario
    @GetMapping("/formulario-roles")
    public String mostrarFormularioRoles() {
        return "buscar-roles";  
    }

    // Consulta los roles activos
    @GetMapping("/roles-por-usuario")
    public String obtenerRolesUsuariosPorUsuario(@RequestParam Long idUsuario, Model model) {
        List<RolUsuarioDTO> roles = usuarioServicio.obtenerRolesUsuariosPorUsuario(idUsuario);
        model.addAttribute("roles", roles);
        return "resultado-roles";  
    }
}
