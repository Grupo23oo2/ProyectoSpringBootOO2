package org.example.turnos.controlador;

import org.example.turnos.dtos.TurnoDTO;
import org.example.turnos.servicios.ITurnoServicio;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/buscar-entre-fechas")
    public String buscarEntreFechas(@RequestParam("desde") LocalDateTime desde,
                                    @RequestParam("hasta") LocalDateTime hasta,
                                    Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnosEntreFechas(desde, hasta);
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }

    @GetMapping("/buscar-por-cliente")
    public String buscarPorClienteEntreFechas(@RequestParam("id") Long id,
                                              @RequestParam("desde") LocalDateTime desde,
                                              @RequestParam("hasta") LocalDateTime hasta,
                                              Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnosDeClienteEntreFechas(id, desde, hasta);
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }

    @GetMapping("/buscar-por-empleado")
    public String buscarPorEmpleadoEntreFechas(@RequestParam("id") Long id,
                                               @RequestParam("desde") LocalDateTime desde,
                                               @RequestParam("hasta") LocalDateTime hasta,
                                               Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnosDeEmpleadoEntreFechas(id, desde, hasta);
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }

    @GetMapping("/buscar-por-lugar")
    public String buscarPorLugarEntreFechas(@RequestParam("id") Integer id,
                                            @RequestParam("desde") LocalDateTime desde,
                                            @RequestParam("hasta") LocalDateTime hasta,
                                            Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnosPorLugarEntreFechas(id, desde, hasta);
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }

    // --- CONSULTAS AVANZADAS ---

    @GetMapping("/buscar-por-presencial")
    public String buscarPorPresencial(@RequestParam("presencial") boolean presencial,
                                      @RequestParam("desde") LocalDateTime desde,
                                      @RequestParam("hasta") LocalDateTime hasta,
                                      Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnosPorPresencialYFechas(presencial, desde, hasta);
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }

    @GetMapping("/buscar-por-nombre-cliente")
    public String buscarPorNombreCliente(@RequestParam("nombre") String nombre,
                                         @RequestParam("desde") LocalDateTime desde,
                                         @RequestParam("hasta") LocalDateTime hasta,
                                         Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnosPorNombreClienteYFechas(nombre, desde, hasta);
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }

    @GetMapping("/buscar-por-rol-empleado")
    public String buscarPorRolEmpleado(@RequestParam("rol") String rol,
                                       @RequestParam("desde") LocalDateTime desde,
                                       @RequestParam("hasta") LocalDateTime hasta,
                                       Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnosPorRolEmpleadoYFechas(rol, desde, hasta);
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }

    @GetMapping("/buscar-por-direccion-lugar")
    public String buscarPorDireccionLugar(@RequestParam("direccion") String direccion,
                                          @RequestParam("desde") LocalDateTime desde,
                                          @RequestParam("hasta") LocalDateTime hasta,
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