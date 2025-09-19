package org.example.turnos.controlador;

import org.example.turnos.dtos.EmpleadoDTO;
import org.example.turnos.excepciones.MiExcepcionPersonalizada;
import org.example.turnos.modelo.Empleado;
import org.example.turnos.repositorios.IEmpleadoRepositorio;
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
    
    @Autowired
    private IEmpleadoRepositorio empleadoRepositorio;


    @GetMapping("/formulario")
    public String mostrarFormularioBusqueda() {
        return "buscar-empleado";
    }

    @GetMapping("/buscar")

    public String buscarPorDni(@RequestParam("dni") String dni, Model model) {
        EmpleadoDTO empleado = empleadoServicio.traerEmpleadoPorDni(dni);
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

    @GetMapping("/editar/{dni}")
    public String mostrarFormularioEdicion(@PathVariable("dni") String dni, Model model) {
    	Empleado empleado = empleadoRepositorio.findByDni(dni)
    		    .orElseThrow(() -> (RuntimeException) new MiExcepcionPersonalizada("Empleado no encontrado con DNI: " + dni));

        model.addAttribute("empleadoEditar", empleado);
        return "resultado-empleados";
    }

    @PostMapping("/modificar")
    public String modificarEmpleado(@RequestParam("dniOriginal") String dniOriginal, @ModelAttribute EmpleadoDTO dto, Model model) {
        EmpleadoDTO actualizado = empleadoServicio.modificarEmpleadoPorDni(dniOriginal, dto);
        model.addAttribute("empleado", actualizado);
        return "resultado-empleados";
    }

    @PostMapping("/eliminar")
    public String eliminarEmpleado(@RequestParam String dni, Model model) {
        try {
        	empleadoServicio.eliminarEmpleadoPorDni(dni);
        	model.addAttribute("mensaje", "Empleado eliminado con éxito (DNI: " + dni + ")");
        } catch (MiExcepcionPersonalizada e) {
            model.addAttribute("error", e.getMessage());
        }
        return "resultado-empleados";
    }
}