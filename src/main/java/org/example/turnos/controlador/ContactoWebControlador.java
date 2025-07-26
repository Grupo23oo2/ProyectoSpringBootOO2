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
    
 // Formulario inicial para gestionar contactos
    @GetMapping("/formulario")
    public String mostrarFormularioContacto(Model model) {
        model.addAttribute("contacto", new ContactoDTO()); // Por si querés usarlo en algún form
        return "buscar-contacto";
    }

    // Traer todos los contactos
    @GetMapping("/todos")
    public String traerTodos(Model model) {
        List<ContactoDTO> contactos = contactoServicio.traerContactos();
        model.addAttribute("contactos", contactos);
        return "resultado-contactos";
    }

 // Buscar contacto por email
    @GetMapping("/buscar-por-email")
    public String buscarPorEmail(@RequestParam String email, Model model) {
        ContactoDTO contacto = contactoServicio.traerContacto(email);
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

 // Modificar contacto por email
    @PostMapping("/modificar")
    public String modificarContacto(@RequestParam String email, @ModelAttribute ContactoDTO dto, Model model) {
        ContactoDTO actualizado = contactoServicio.modificarContacto(email, dto);
        model.addAttribute("contacto", actualizado);
        return "resultado-contacto";
    }

 // Eliminar contacto por email
    @PostMapping("/eliminar")
    public String eliminarContacto(@RequestParam String email, Model model) {
        contactoServicio.eliminarContacto(email);
        model.addAttribute("mensaje", "Contacto eliminado con éxito (Email: " + email + ")");
        return "resultado-contacto";
    }
}
