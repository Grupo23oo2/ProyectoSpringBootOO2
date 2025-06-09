package org.example.controlador;

import org.example.dtos.EmpleadoDTO;
import org.example.servicios.IEmpleadoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/empleados")
public class EmpleadoWebControlador {

    @Autowired
    private IEmpleadoServicio empleadoServicio;

    @GetMapping("/formulario")
    public String mostrarFormularioBusqueda() {
        return "buscar-empleado";
    }

    @GetMapping("/buscar-por-role")
    public String buscarPorRole(@RequestParam("role") String role, Model model) {
        List<EmpleadoDTO> empleados = empleadoServicio.empleadosPorRol(role);
        model.addAttribute("empleados", empleados);
        return "resultado-empleados";
    }

    @GetMapping("/buscar-por-fecha")
    public String buscarPorFecha(@RequestParam("fecha") String fecha, Model model) {
        LocalDate fechaInicio = LocalDate.parse(fecha);
        List<EmpleadoDTO> empleados = empleadoServicio.empleadosPorFechaInicio(fechaInicio);
        model.addAttribute("empleados", empleados);
        return "resultado-empleados";
    }
}
