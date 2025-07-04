package org.example.turnos.controlador;

import java.util.List;

import org.example.turnos.dtos.ServicioDTO;
import org.example.turnos.servicios.IServicioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/servicios")
public class ServicioWebControlador {

    @Autowired
    private IServicioServicio servicioServicio;

    // Mostrar formulario 
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("servicioDTO", new ServicioDTO());
        return "buscar-servicio";  
    }

    // Traer todos los servicios
    @GetMapping("/todos")
    public String traerTodos(Model model) {
        List<ServicioDTO> servicios = servicioServicio.traerServicios();
        model.addAttribute("servicios", servicios);
        return "resultado-servicios";  
    }

    // Buscar servicio por ID
    @GetMapping("/buscar-por-id")
    public String buscarPorId(@RequestParam Long id, Model model) {
        ServicioDTO servicio = servicioServicio.traerServicio(id);
        model.addAttribute("servicio", servicio);
        return "resultado-servicio"; 
    }

    // Agregar nuevo servicio
    @PostMapping("/agregar")
    public String agregarServicio(@ModelAttribute ServicioDTO servicioDTO, Model model) {
        ServicioDTO agregado = servicioServicio.agregarServicio(servicioDTO);
        model.addAttribute("servicio", agregado);
        return "resultado-servicio";  
    }

    // Modificar servicio
    @PostMapping("/modificar")
    public String modificarServicio(@RequestParam Long id, @ModelAttribute ServicioDTO servicioDTO, Model model) {
        ServicioDTO actualizado = servicioServicio.modificarServicio(id, servicioDTO);
        model.addAttribute("servicio", actualizado);
        return "resultado-servicio"; 
    }

    // Eliminar servicio
    @PostMapping("/eliminar")
    public String eliminarServicio(@RequestParam Long id, Model model) {
        servicioServicio.eliminarServicio(id);
        model.addAttribute("mensaje", "Servicio eliminado con éxito (ID: " + id + ")");
        return "resultado-servicio";  
    }
}