package org.example.turnos.controlador;

import java.time.LocalDate;

import org.example.turnos.dtos.ClienteDTO;
import org.example.turnos.dtos.ContactoDTO;
import org.example.turnos.dtos.EmpleadoDTO;
import org.example.turnos.dtos.UsuarioDTO;
import org.example.turnos.servicios.IClienteServicio;
import org.example.turnos.servicios.IContactoServicio;
import org.example.turnos.servicios.IEmpleadoServicio;
import org.example.turnos.servicios.IUsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/registro")
public class RegistroWebControlador {

    @Autowired
    private IEmpleadoServicio empleadoServicio;

    @Autowired
    private IClienteServicio clienteServicio;
    
    @Autowired
    private IContactoServicio contactoServicio;

    @Autowired
    private IUsuarioServicio usuarioServicio;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/formulario-empleado")
    public String mostrarFormularioEmpleado() {
        return "registrarEmpleadoUsuario";
    }

    @GetMapping("/formulario-cliente")
    public String mostrarFormularioCliente() {
        return "registrarClienteUsuario";
    }

    @PostMapping("/alta-empleado-usuario")
    public String registrarEmpleadoYUsuario(
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String dni,
            @RequestParam String fechaInicio,
            @RequestParam String nombreUsuario,
            @RequestParam String email,
            @RequestParam String contraseniaUsuario,
            @RequestParam(required = false) boolean estado,
            Model model,
            RedirectAttributes redirectAttributes) {

        // 1. Crear y guardar el empleado
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();
        empleadoDTO.setNombre(nombre);
        empleadoDTO.setApellido(apellido);
        empleadoDTO.setDni(dni);
        empleadoDTO.setFechaInicio(LocalDate.parse(fechaInicio));

        EmpleadoDTO nuevoEmpleado = empleadoServicio.agregarEmpleado(empleadoDTO);

        // 2. Crear y guardar el usuario si el empleado se creó bien
        if (nuevoEmpleado != null && nuevoEmpleado.getIdPersona() != null) {
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setIdPersona(nuevoEmpleado.getIdPersona());
            usuarioDTO.setNombreUsuario(nombreUsuario);
            usuarioDTO.setEmail(email);
            usuarioDTO.setContraseniaUsuario(passwordEncoder.encode(contraseniaUsuario));
            usuarioDTO.setEstado(true); //ponemos directamente que va a estar activo, antes se podia cambiar en el formulario de registro
            usuarioDTO.setRol("ROLE_EMPLEADO"); //como esta en la pagina de empleado, no hace falta que lo reciba del formulario, lo ponemos directamente

            usuarioServicio.agregarUsuario(usuarioDTO);

            redirectAttributes.addFlashAttribute("mensaje", "Registro exitoso. Ahora puede iniciar sesión."); //login si se grabo todo
            
            return "redirect:/login";
        }

        model.addAttribute("error", "No se pudo registrar el empleado y usuario. Verificá los datos e intentá de nuevo."); //se queda en la misma pagina en caso de error
        
        return "registrarEmpleadoUsuario";
    }
    
    @PostMapping("/alta-cliente-usuario")
    public String registrarClienteCompleto(
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String dni,
            @RequestParam String cuit,
            @RequestParam String telefono,
            @RequestParam String emailContacto,
            @RequestParam String direccion,
            @RequestParam String nombreUsuario,
            @RequestParam String emailUsuario,
            @RequestParam String contraseniaUsuario,
            @RequestParam(defaultValue = "true") boolean estado,
            Model model,
            RedirectAttributes redirectAttributes) {

        ClienteDTO cliente = new ClienteDTO();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setDni(dni);
        cliente.setCuit(cuit);

        ClienteDTO nuevoCliente = clienteServicio.agregarCliente(cliente);

        if (nuevoCliente != null && nuevoCliente.getIdPersona() != null) {
            // Crear contacto con el mismo ID de persona
            ContactoDTO contacto = new ContactoDTO();
            contacto.setIdContacto(nuevoCliente.getIdPersona());
            contacto.setTelefono(telefono);
            contacto.setEmail(emailContacto);
            contacto.setDireccion(direccion);
            contactoServicio.agregarContacto(contacto);

            // Crear usuario asociado al cliente
            UsuarioDTO usuario = new UsuarioDTO();
            usuario.setIdPersona(nuevoCliente.getIdPersona());
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setEmail(emailUsuario);
            usuario.setContraseniaUsuario(passwordEncoder.encode(contraseniaUsuario));
            usuario.setEstado(true); //ponemos directamente que va a estar activo, antes se podia cambiar en el formulario de registro
            usuario.setRol("ROLE_CLIENTE"); //como esta en la pagina de empleado, no hace falta que lo reciba del formulario, lo ponemos directamente

            usuarioServicio.agregarUsuario(usuario);

            redirectAttributes.addFlashAttribute("mensaje", "Registro exitoso. Ahora puede iniciar sesión."); //login si se grabo todo
            
            return "redirect:/login"; // Redirige a login si todo fue bien
        }

        model.addAttribute("error", "No se pudo registrar el cliente y usuario. Verificá los datos e intentá de nuevo."); //se queda en la misma pagina en caso de error
        
        return "registrarClienteUsuario";
    }

}
