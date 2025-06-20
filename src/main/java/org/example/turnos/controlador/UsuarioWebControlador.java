package org.example.turnos.controlador;

import org.example.turnos.dtos.RolUsuarioDTO;
import org.example.turnos.dtos.UsuarioDTO;
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

    // Formulario para ingresar ID de usuario para ver roles
    @GetMapping("/formulario-roles")
    public String mostrarFormularioRoles(Model model) {
    	model.addAttribute("usuario", new UsuarioDTO());
        return "buscar-roles";
    }

    // Consulta los roles activos de un usuario
    @GetMapping("/roles-por-usuario")
    public String obtenerRolesUsuariosPorUsuario(@RequestParam Long idUsuario, Model model) {
        List<RolUsuarioDTO> roles = usuarioServicio.obtenerRolesUsuariosPorUsuario(idUsuario);
        model.addAttribute("roles", roles);
        return "resultado-roles";
    }

    // ABM USUARIOS

    // Mostrar todos los usuarios
    @GetMapping("/todos")
    public String traerTodos(Model model) {
        List<UsuarioDTO> usuarios = usuarioServicio.traerUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "resultado-usuarios";
    }

    // Buscar por ID
    @GetMapping("/buscar-por-id")
    public String buscarPorId(@RequestParam Long id, Model model) {
        UsuarioDTO usuario = usuarioServicio.traerUsuarioPorId(id);
        model.addAttribute("usuario", usuario);
        return "resultado-usuario";
    }

    // Agregar usuario
    @PostMapping("/agregar")
    public String agregarUsuario(@ModelAttribute UsuarioDTO usuarioDTO, Model model) {
        UsuarioDTO agregado = usuarioServicio.agregarUsuario(usuarioDTO);
        model.addAttribute("usuario", agregado);
        return "resultado-usuario";
    }

    // Modificar usuario
    @PostMapping("/modificar")
    public String modificarUsuario(@RequestParam Long id, @ModelAttribute UsuarioDTO usuarioDTO, Model model) {
        UsuarioDTO actualizado = usuarioServicio.modificarUsuario(id, usuarioDTO);
        model.addAttribute("usuario", actualizado);
        return "resultado-usuario";
    }

    // Eliminar usuario
    @PostMapping("/eliminar")
    public String eliminarUsuario(@RequestParam Long id, Model model) {
        usuarioServicio.eliminarUsuario(id);
        model.addAttribute("mensaje", "Usuario eliminado con éxito (ID: " + id + ")");
        return "resultado-usuario";
    }
}
