package org.example.turnos.controlador;

import org.example.turnos.dtos.EmpleadoDTO;
import org.example.turnos.servicios.IEmpleadoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

    @GetMapping("/buscar")
    public String buscarPorId(@RequestParam("id") Long id, Model model) {
        EmpleadoDTO empleado = empleadoServicio.traerEmpleadoPorId(id);
        model.addAttribute("empleado", empleado);
        return "resultado-empleados";
    }

    @GetMapping("/todos")
    public String traerTodos(Model model) {
        List<EmpleadoDTO> empleados = empleadoServicio.traerEmpleados();
        model.addAttribute("empleados", empleados);
        return "resultado-empleados";
    }

    @GetMapping("/buscar-por-rol")
    public String buscarPorRol(@RequestParam("rol") String rol, Model model) {
        List<EmpleadoDTO> empleados = empleadoServicio.empleadosPorRol(rol);
        model.addAttribute("empleados", empleados);
        return "resultado-empleados";
    }

    @GetMapping("/buscar-por-fecha")
    public String buscarPorFechaInicio(@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha, Model model) {
        List<EmpleadoDTO> empleados = empleadoServicio.empleadosPorFechaInicio(fecha);
        model.addAttribute("empleados", empleados);
        return "resultado-empleados";
    }

    @PostMapping("/agregar")
    public String agregarEmpleado(@ModelAttribute EmpleadoDTO dto, Model model) {
        EmpleadoDTO agregado = empleadoServicio.agregarEmpleado(dto);
        model.addAttribute("empleado", agregado);
        return "resultado-empleados";
    }

    @PostMapping("/modificar")
    public String modificarEmpleado(@RequestParam Long id, @ModelAttribute EmpleadoDTO dto, Model model) {
        EmpleadoDTO actualizado = empleadoServicio.modificarEmpleado(id, dto);
        model.addAttribute("empleado", actualizado);
        return "resultado-empleados";
    }

    @PostMapping("/eliminar")
    public String eliminarEmpleado(@RequestParam Long id, Model model) {
        empleadoServicio.eliminarEmpleado(id);
        model.addAttribute("mensaje", "Empleado eliminado con éxito (ID: " + id + ")");
        return "resultado-empleados";
    }
}