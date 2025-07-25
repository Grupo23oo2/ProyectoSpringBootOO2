package org.example.turnos.controlador;

import java.util.List;

import org.example.turnos.dtos.ContactoDTO;
import org.example.turnos.servicios.IContactoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/contactos")
public class ContactoWebControlador {

    @Autowired
    private IContactoServicio contactoServicio;

    // Traer todos los contactos
    @GetMapping("/todos")
    public String traerTodos(Model model) {
        List<ContactoDTO> contactos = contactoServicio.traerContactos();
        model.addAttribute("contactos", contactos);
        return "resultado-contactos";
    }

    // Buscar contacto por ID
    @GetMapping("/buscar-por-id")
    public String buscarPorId(@RequestParam Long id, Model model) {
        ContactoDTO contacto = contactoServicio.traerContacto(id);
        model.addAttribute("contacto", contacto);
        return "resultado-contacto";
    }

    // Agregar contacto
    @PostMapping("/agregar")
    public String agregarContacto(@ModelAttribute ContactoDTO dto, Model model) {
        ContactoDTO agregado = contactoServicio.agregarContacto(dto);
        model.addAttribute("contacto", agregado);
        return "resultado-contacto";
    }

    // Modificar contacto
    @PostMapping("/modificar")
    public String modificarContacto(@RequestParam Long id, @ModelAttribute ContactoDTO dto, Model model) {
        ContactoDTO actualizado = contactoServicio.modificarContacto(id, dto);
        model.addAttribute("contacto", actualizado);
        return "resultado-contacto";
    }

    // Eliminar contacto
    @PostMapping("/eliminar")
    public String eliminarContacto(@RequestParam Long id, Model model) {
        contactoServicio.eliminarContacto(id);
        model.addAttribute("mensaje", "Contacto eliminado con éxito (ID: " + id + ")");
        return "resultado-contacto";
    }
}
