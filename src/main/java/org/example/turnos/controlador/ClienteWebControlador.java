package org.example.turnos.controlador;

import org.example.turnos.dtos.ClienteDTO;
import org.example.turnos.dtos.ContactoDTO;
import org.example.turnos.servicios.IClienteServicio;
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

    @GetMapping("/formulario")
    public String mostrarFormularioBusqueda() {
        return "buscar-cliente";
    }

    @GetMapping("/buscar")
    public String buscarPorCuit(@RequestParam("cuit") String cuit, Model model) {
        List<ClienteDTO> clientes = clienteServicio.clientesPorCuit(cuit);
        model.addAttribute("clientes", clientes);
        return "resultado-clientes";
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

    @GetMapping("/buscar-por-id")
    public String traerPorId(@RequestParam("id") Long id, Model model) {
        Optional<ClienteDTO> clienteOpt = clienteServicio.traerClientePorId(id);
        if (clienteOpt.isPresent()) {
            model.addAttribute("cliente", clienteOpt.get());
        } else {
            model.addAttribute("mensaje", "Cliente no encontrado con ID: " + id);
        }
        return "resultado-cliente";
    }

    @PostMapping("/agregar")
    public String agregarCliente(@ModelAttribute ClienteDTO dto, Model model) {
        ClienteDTO agregado = clienteServicio.agregarCliente(dto);
        model.addAttribute("cliente", agregado);
        return "resultado-cliente";
    }

    @PostMapping("/modificar")
    public String modificarCliente(@RequestParam Long id, @ModelAttribute ClienteDTO dto, Model model) {
        Optional<ClienteDTO> modificado = clienteServicio.modificarCliente(id, dto);
        if (modificado.isPresent()) {
            model.addAttribute("cliente", modificado.get());
        } else {
            model.addAttribute("mensaje", "No se pudo modificar el cliente con ID: " + id);
        }
        return "resultado-cliente";
    }

    @PostMapping("/eliminar")
    public String eliminarCliente(@RequestParam Long id, Model model) {
        clienteServicio.eliminarCliente(id);
        model.addAttribute("mensaje", "Cliente eliminado correctamente (ID: " + id + ")");
        return "resultado-cliente";
    }
}
