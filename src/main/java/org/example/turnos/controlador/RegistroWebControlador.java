package org.example.turnos.controlador;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.example.turnos.dtos.ClienteDTO;
import org.example.turnos.dtos.ContactoDTO;
import org.example.turnos.dtos.EmpleadoDTO;
import org.example.turnos.dtos.UsuarioDTO;
import org.example.turnos.modelo.Cliente;
import org.example.turnos.modelo.Contacto;
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
    	
    	EmpleadoDTO empleadoDTO = new EmpleadoDTO(
    		    null,                  // idPersona (null porque todavía no existe)
    		    nombre,
    		    apellido,
    		    dni,
    		    null,                  // idUsuario (null porque aún no tiene usuario)
    		    LocalDate.parse(fechaInicio)
    		);

    		EmpleadoDTO nuevoEmpleado = empleadoServicio.agregarEmpleado(empleadoDTO);

/*        EmpleadoDTO empleadoDTO = new EmpleadoDTO();
        empleadoDTO.setNombre(nombre);
        empleadoDTO.setApellido(apellido);
        empleadoDTO.setDni(dni);
        empleadoDTO.setFechaInicio(LocalDate.parse(fechaInicio));

        EmpleadoDTO nuevoEmpleado = empleadoServicio.agregarEmpleado(empleadoDTO);
*/
       // 2. Crear y guardar el usuario si el empleado se creó bien
        if (nuevoEmpleado != null && nuevoEmpleado.idPersona()!= null) {
        	
          /*  UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setIdPersona(nuevoEmpleado.idPersona());
            usuarioDTO.setNombreUsuario(nombreUsuario);
            usuarioDTO.setEmail(email);
            usuarioDTO.setContraseniaUsuario(passwordEncoder.encode(contraseniaUsuario));
            usuarioDTO.setEstado(true); //ponemos directamente que va a estar activo, antes se podia cambiar en el formulario de registro
            usuarioDTO.setRol("ROLE_EMPLEADO"); //como esta en la pagina de empleado, no hace falta que lo reciba del formulario, lo ponemos directamente
          */

        	UsuarioDTO usuarioDTO = new UsuarioDTO(
        	null,                               // idUsuario, todavía no existe
        	nombreUsuario,                       // nombreUsuario
        	passwordEncoder.encode(contraseniaUsuario), // contraseniaUsuario
        	true,                                 // estado
        	nuevoEmpleado.idPersona(),            // idPersona
        	email,                                // email
        	"ROLE_EMPLEADO",                      // rol
        	LocalDateTime.now()                    // fechaCreacion
        );	
        	    
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

        try {
            Cliente cliente = new Cliente();
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setDni(dni);
            cliente.setCuit(cuit);

            Contacto contacto = new Contacto();
            contacto.setDireccion(direccion);
            contacto.setEmail(emailContacto);
            contacto.setTelefono(telefono);

            // Guardar en cascada
            Cliente nuevoCliente = clienteServicio.guardarClienteConContacto(cliente, contacto);

            // Crear Usuario
            UsuarioDTO usuario = new UsuarioDTO(
                    null,
                    nombreUsuario,
                    passwordEncoder.encode(contraseniaUsuario),
                    estado,
                    nuevoCliente.getIdPersona(),
                    emailUsuario,
                    "ROLE_CLIENTE",
                    LocalDateTime.now()
            );
            usuarioServicio.agregarUsuario(usuario);

            redirectAttributes.addFlashAttribute("mensaje", "Registro exitoso. Ahora puede iniciar sesión.");
            return "redirect:/login";

        } catch (Exception e) {
            model.addAttribute("error", "No se pudo registrar el cliente: " + e.getMessage());
            return "registrarClienteUsuario";
        }
    }
}
