package org.example.turnos.controlador;

import org.example.turnos.dtos.ClienteDTO;
import org.example.turnos.dtos.ContactoDTO;
import org.example.turnos.excepciones.MiExcepcionPersonalizada;
import org.example.turnos.servicios.IClienteServicio;
import org.example.turnos.servicios.IContactoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clientes")
public class ClienteWebControlador {

    @Autowired
    private IClienteServicio clienteServicio;
    
    @Autowired
    private IContactoServicio contactoServicio;

    @GetMapping("/formulario")
    public String mostrarFormularioBusqueda() {
        return "buscar-cliente";
    }

    @GetMapping("/buscar")
    public String findAllByCuit(@RequestParam("cuit") String cuit, Model model) {
        List<ClienteDTO> clientes = clienteServicio.findAllByCuit(cuit);
        model.addAttribute("clientes", clientes);
        return "resultado-clientes";
    }
    
    @GetMapping("/buscar-por-cuit")
    public String findByCuit(@RequestParam("cuit") String cuit, Model model) {
        try {
            ClienteDTO cliente = clienteServicio.findByCuit(cuit);
            model.addAttribute("cliente", cliente);
            return "resultado-cliente"; 
        } catch (MiExcepcionPersonalizada e) {
            model.addAttribute("error", e.getMessage());
            return "error-cliente"; 
        }
    }

    @GetMapping("/buscar-por-role")
    public String buscarPorRole(@RequestParam("role") String role, Model model) {
        List<ClienteDTO> clientes = clienteServicio.clientesPorRol(role);
        model.addAttribute("clientes", clientes);
        return "resultado-clientes";
    }

    @GetMapping("/contacto-por-cuit")
    public String contactoPorCuit(@RequestParam String cuit, Model model) {
        ContactoDTO contacto = clienteServicio.buscarContactoPorCuit(cuit);
        model.addAttribute("contacto", contacto);
        return "resultado-contacto";
    }

    @GetMapping("/todos")
    public String traerTodos(Model model) {
        List<ClienteDTO> clientes = clienteServicio.traerClientes();
        model.addAttribute("clientes", clientes);
        return "resultado-clientes";
    }

    @GetMapping("/buscar-por-dni")
    public String traerPorDni(@RequestParam("dni") String dni, Model model) {
        Optional<ClienteDTO> clienteOpt = clienteServicio.traerClientePorDni(dni);
        if (clienteOpt.isPresent()) {
            model.addAttribute("cliente", clienteOpt.get());
        } else {
            model.addAttribute("mensaje", "Cliente no encontrado con DNI: " + dni);
        }
        return "resultado-cliente";
    }

    @PostMapping("/agregar")
    public String agregarCliente(@ModelAttribute ClienteDTO dto, Model model) {
        ClienteDTO agregado = clienteServicio.agregarCliente(dto);
        model.addAttribute("cliente", agregado);
        return "resultado-cliente";
    }
    
    @GetMapping("/editar/{dni}")
    public String mostrarFormularioEdicion(@PathVariable("dni") String dni, Model model) {
        Optional<ClienteDTO> clienteOpt = clienteServicio.traerClientePorDni(dni);
        if (clienteOpt.isPresent()) {
        	ClienteDTO cliente = clienteOpt.get();
            model.addAttribute("clienteEditar", cliente);

            // Obtener contacto por idPersona o dni
            ContactoDTO contacto = contactoServicio.traerContacto(cliente.getDni());
            model.addAttribute("contactoEditar", contacto); // <- nuevo atributo
        } else {
            model.addAttribute("mensaje", "Cliente no encontrado con DNI: " + dni);
        }
        return "resultado-clientes";
    }

    @PostMapping("/modificar")
    public String modificarCliente(@RequestParam("dniOriginal") String dniOriginal, @ModelAttribute ClienteDTO dto, Model model) {
        Optional<ClienteDTO> modificado = clienteServicio.modificarClientePorDni(dniOriginal, dto);
        if (modificado.isPresent()) {
            model.addAttribute("cliente", modificado.get());
        } else {
            model.addAttribute("mensaje", "No se pudo modificar el cliente con DNI: " + dniOriginal);
        }
        return "resultado-cliente";
    }

    @PostMapping("/eliminar")
    public String eliminarCliente(@RequestParam String dni, Model model) {
        clienteServicio.eliminarClientePorDni(dni);
        model.addAttribute("mensaje", "Cliente eliminado correctamente (DNI: " + dni + ")");
        return "resultado-clientes";
    }
}
