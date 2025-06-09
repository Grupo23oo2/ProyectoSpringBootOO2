package org.example.controlador;

import org.example.dtos.ClienteDTO;
import org.example.dtos.ContactoDTO;
import org.example.servicios.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


}
