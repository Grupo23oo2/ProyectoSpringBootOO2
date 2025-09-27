package org.example.turnos.controlador;

import org.example.turnos.dtos.UsuarioDTO;
import org.example.turnos.excepciones.MiExcepcionPersonalizada;
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
    @GetMapping("/formulario")
    public String mostrarFormularioRoles(Model model) {
    	
    	//model.addAttribute("usuario", new UsuarioDTO());
    	
    	model.addAttribute("usuario", new UsuarioDTO(//cambia por la RecordClass
    		    null,       // idUsuario
    		    null,       // nombreUsuario
    		    null,       // contraseniaUsuario
    		    false,      // estado
    		    null,       // idPersona
    		    null,       // email
    		    null,       // rol
    		    null        // fechaCreacion
    		));
    	
        return "buscar-usuario";
    }

    // ABM USUARIOS
    // Mostrar todos los usuarios
    @GetMapping("/todos")
    public String traerTodos(Model model) {
        List<UsuarioDTO> usuarios = usuarioServicio.traerUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "resultado-usuarios";
    }

    // Buscar por email
    @GetMapping("/buscar-por-email")
    public String buscarPorEmail(@RequestParam String email, Model model) {
        UsuarioDTO usuario = usuarioServicio.traerUsuarioPorEmail(email);
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

    @PostMapping("/modificar")
    public String modificarUsuario(@RequestParam String emailActual, @RequestParam String emailNuevo, @ModelAttribute UsuarioDTO usuarioDTO, Model model) {
        try {
            //Creamos un DTO con el email actualizado y mantenemos rol y fechaCreacion
            UsuarioDTO dtoActualizado = new UsuarioDTO(
                usuarioDTO.idUsuario(),
                usuarioDTO.nombreUsuario(),
                usuarioDTO.contraseniaUsuario(),
                usuarioDTO.estado(),
                usuarioDTO.idPersona(),
                emailNuevo, //reemplazamos email por el nuevo
                usuarioDTO.rol(), //rol se mantiene
                usuarioDTO.fechaCreacion() //fecha de creación se mantiene
            );

            UsuarioDTO actualizado = usuarioServicio.modificarUsuario(emailActual, dtoActualizado);
            model.addAttribute("usuario", actualizado);
        } catch (MiExcepcionPersonalizada e) {
            model.addAttribute("error", e.getMessage());
        }
        return "resultado-usuario";
    }

    @PostMapping("/eliminar")
    public String eliminarUsuario(@RequestParam String email, Model model) {
        usuarioServicio.eliminarUsuario(email);
        model.addAttribute("mensaje", "Usuario eliminado con éxito (Email: " + email + ")");
        return "resultado-usuario";
    }
}