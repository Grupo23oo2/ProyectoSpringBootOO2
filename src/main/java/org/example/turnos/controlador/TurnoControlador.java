package org.example.turnos.controlador;

import org.example.turnos.dtos.TurnoDTO;
import org.example.turnos.servicios.ITurnoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/turnos")
public class TurnoControlador {

    @Autowired
    private ITurnoServicio turnoServicio;

    @GetMapping("/formulario")
    public String mostrarFormulario() {
        return "buscar-turnos"; // Vista con el formulario de búsqueda
    }
    
    
 // --- ABM Turnos ---

    @GetMapping("/todos")
    public String traerTodosLosTurnos(Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnos();
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }

    @GetMapping("/buscar-por-id")
    public String buscarPorId(@RequestParam("id") Long id, Model model) {
        TurnoDTO turno = turnoServicio.traerTurno(id);
        model.addAttribute("turno", turno);
        return "resultado-turno";
    }

    @PostMapping("/agregar")
    public String agregarTurno(@ModelAttribute TurnoDTO dto, Model model) {
        TurnoDTO agregado = turnoServicio.agregarTurno(dto);
        model.addAttribute("turno", agregado);
        return "resultado-turno";
    }

    @PostMapping("/modificar")
    public String modificarTurno(@RequestParam Long id, @ModelAttribute TurnoDTO dto, Model model) {
        TurnoDTO actualizado = turnoServicio.modificarTurno(id, dto);
        model.addAttribute("turno", actualizado);
        return "resultado-turno";
    }

    @PostMapping("/eliminar")
    public String eliminarTurno(@RequestParam Long id, Model model) {
        turnoServicio.eliminarTurno(id);
        model.addAttribute("mensaje", "Turno eliminado con éxito (ID: " + id + ")");
        return "resultado-turno";
    }

    // --- CONSULTAS POR FILTROS ---
    
    @GetMapping("/buscar-entre-fechas")
    public String buscarEntreFechas(@RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
                                    @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
                                    Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnosEntreFechas(desde, hasta);
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }

    @GetMapping("/buscar-por-cliente")
    public String buscarPorClienteEntreFechas(@RequestParam("id") Long id,
                                              @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
                                              @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
                                              Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnosDeClienteEntreFechas(id, desde, hasta);
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }

    @GetMapping("/buscar-por-empleado")
    public String buscarPorEmpleadoEntreFechas(@RequestParam("id") Long id,
                                               @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
                                               @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
                                               Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnosDeEmpleadoEntreFechas(id, desde, hasta);
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }

    @GetMapping("/buscar-por-lugar")
    public String buscarPorLugarEntreFechas(@RequestParam("id") Integer id,
                                            @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
                                            @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
                                            Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnosPorLugarEntreFechas(id, desde, hasta);
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }

    // --- CONSULTAS AVANZADAS ---

    @GetMapping("/buscar-por-presencial")
    public String buscarPorPresencial(@RequestParam("presencial") boolean presencial,
                                      @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
                                      @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
                                      Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnosPorPresencialYFechas(presencial, desde, hasta);
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }

    @GetMapping("/buscar-por-nombre-cliente")
    public String buscarPorNombreCliente(@RequestParam("nombre") String nombre,
                                         @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
                                         @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
                                         Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnosPorNombreClienteYFechas(nombre, desde, hasta);
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }

    @GetMapping("/buscar-por-rol-empleado")
    public String buscarPorRolEmpleado(@RequestParam("rol") String rol,
                                       @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
                                       @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
                                       Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnosPorRolEmpleadoYFechas(rol, desde, hasta);
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }

    @GetMapping("/buscar-por-direccion-lugar")
    public String buscarPorDireccionLugar(@RequestParam("direccion") String direccion,
                                          @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
                                          @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
                                          Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnosPorDireccionLugarYFechas(direccion, desde, hasta);
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }

    @GetMapping("/turnos-presenciales")
    public String mostrarTurnosPresenciales(Model model) {
        List<TurnoDTO> turnos = turnoServicio.obtenerTurnosPresenciales(true);
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }

    @GetMapping("/buscar-por-apellido-empleado")
    public String buscarTurnosPorApellidoEmpleado(@RequestParam("apellido") String apellido, Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnosPorApellidoEmpleado(apellido);
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }

    
}