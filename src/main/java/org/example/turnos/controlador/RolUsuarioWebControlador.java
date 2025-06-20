package org.example.turnos.controlador;

import java.util.List;

import org.example.turnos.dtos.RolUsuarioDTO;
import org.example.turnos.servicios.IRolUsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/roles-usuarios")
public class RolUsuarioWebControlador {

    @Autowired
    private IRolUsuarioServicio rolUsuarioServicio;
    
    @GetMapping ("/formulario-roles-usuarios")
    public String mostrarPaginaInicial(Model model) {
        // Podés preparar datos o DTOs si los necesitás en la vista
        model.addAttribute("rolUsuarioDTO", new RolUsuarioDTO());
        return "formulario-roles-usuarios";  // Nombre de la plantilla Thymeleaf para el formulario inicial
    }
    
    
    // Mostrar todos los roles de usuario
    @GetMapping("/todos")
    public String traerTodos(Model model) {
        List<RolUsuarioDTO> roles = rolUsuarioServicio.traerRolesUsuarios();
        model.addAttribute("roles", roles);
        return "resultado-roles";
    }

    // Buscar un rol-usuario por ID
    @GetMapping("/buscar-por-id")
    public String buscarPorId(@RequestParam Long id, Model model) {
        RolUsuarioDTO rol = rolUsuarioServicio.traerRolUsuario(id);
        model.addAttribute("rol", rol);
        return "resultado-rol";
    }

    // Agregar un nuevo rol-usuario
    @PostMapping("/agregar")
    public String agregarRolUsuario(@ModelAttribute RolUsuarioDTO dto, Model model) {
        RolUsuarioDTO agregado = rolUsuarioServicio.agregarRolUsuario(dto);
        model.addAttribute("rol", agregado);
        return "resultado-rol";
    }

    // Modificar un rol-usuario
    @PostMapping("/modificar")
    public String modificarRolUsuario(@RequestParam Long id, @ModelAttribute RolUsuarioDTO dto, Model model) {
        RolUsuarioDTO actualizado = rolUsuarioServicio.modificarRolUsuario(id, dto);
        model.addAttribute("rol", actualizado);
        return "resultado-rol";
    }

    // Eliminar un rol-usuario
    @PostMapping("/eliminar")
    public String eliminarRolUsuario(@RequestParam Long id, Model model) {
        rolUsuarioServicio.eliminarRolUsuario(id);
        model.addAttribute("mensaje", "Rol-usuario eliminado con éxito (ID: " + id + ")");
        return "resultado-rol";
    }
}