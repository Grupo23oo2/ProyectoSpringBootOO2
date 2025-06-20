package org.example.turnos.controlador;

import java.util.List;

import org.example.turnos.dtos.LugarDTO;
import org.example.turnos.servicios.ILugarServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/lugares")
public class LugarWebControlador {

    @Autowired
    private ILugarServicio lugarServicio;

    // Traer todos los lugares
    @GetMapping("/todos")
    public String traerTodos(Model model) {
        List<LugarDTO> lugares = lugarServicio.traerLugares();
        model.addAttribute("lugares", lugares);
        return "resultado-lugares";
    }

    // Buscar lugar por ID
    @GetMapping("/buscar-por-id")
    public String buscarPorId(@RequestParam Long id, Model model) {
        LugarDTO lugar = lugarServicio.traerLugar(id);
        model.addAttribute("lugar", lugar);
        return "resultado-lugar";
    }

    // Agregar lugar
    @PostMapping("/agregar")
    public String agregarLugar(@ModelAttribute LugarDTO dto, Model model) {
        LugarDTO agregado = lugarServicio.agregarLugar(dto);
        model.addAttribute("lugar", agregado);
        return "resultado-lugar";
    }

    // Modificar lugar
    @PostMapping("/modificar")
    public String modificarLugar(@RequestParam Long id, @ModelAttribute LugarDTO dto, Model model) {
        LugarDTO actualizado = lugarServicio.modificarLugar(id, dto);
        model.addAttribute("lugar", actualizado);
        return "resultado-lugar";
    }

    // Eliminar lugar
    @PostMapping("/eliminar")
    public String eliminarLugar(@RequestParam Long id, Model model) {
        lugarServicio.eliminarLugar(id);
        model.addAttribute("mensaje", "Lugar eliminado con éxito (ID: " + id + ")");
        return "resultado-lugar";
    }
}
