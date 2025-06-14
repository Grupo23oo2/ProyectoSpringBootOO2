package org.example.turnos.controlador;

import org.example.turnos.dtos.ServicioDTO;
import org.example.turnos.servicios.IServicioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/servicios")
public class ServicioControlador {

    @Autowired
    private IServicioServicio servicioServicio;

    @GetMapping("/formulario")
    public String mostrarFormulario() {
        return "buscar-servicios"; // Vista con el formulario de búsqueda
    }

    @GetMapping("/buscar-entre-fechas")
    public String buscarEntreFechas(@RequestParam("desde") LocalDateTime desde,
                                    @RequestParam("hasta") LocalDateTime hasta,
                                    Model model) {
        List<ServicioDTO> servicios = servicioServicio.traerServiciosEntreFechas(desde, hasta);
        model.addAttribute("servicios", servicios);
        return "resultado-servicios";
    }

    @GetMapping("/buscar-por-cliente")
    public String buscarPorClienteEntreFechas(@RequestParam("id") Long id,
                                              @RequestParam("desde") LocalDateTime desde,
                                              @RequestParam("hasta") LocalDateTime hasta,
                                              Model model) {
        List<ServicioDTO> servicios = servicioServicio.traerServiciosDeClienteEntreFechas(id, desde, hasta);
        model.addAttribute("servicios", servicios);
        return "resultado-servicios";
    }

    @GetMapping("/buscar-por-empleado")
    public String buscarPorEmpleadoEntreFechas(@RequestParam("id") Long id,
                                               @RequestParam("desde") LocalDateTime desde,
                                               @RequestParam("hasta") LocalDateTime hasta,
                                               Model model) {
        List<ServicioDTO> servicios = servicioServicio.traerServiciosDeEmpleadoEntreFechas(id, desde, hasta);
        model.addAttribute("servicios", servicios);
        return "resultado-servicios";
    }

    @GetMapping("/buscar-por-lugar")
    public String buscarPorLugarEntreFechas(@RequestParam("id") Integer id,
                                            @RequestParam("desde") LocalDateTime desde,
                                            @RequestParam("hasta") LocalDateTime hasta,
                                            Model model) {
        List<ServicioDTO> servicios = servicioServicio.traerServiciosPorLugarEntreFechas(id, desde, hasta);
        model.addAttribute("servicios", servicios);
        return "resultado-servicios";
    }

    // --- CONSULTAS AVANZADAS ---

    @GetMapping("/buscar-por-presencial")
    public String buscarPorPresencial(@RequestParam("presencial") boolean presencial,
                                      @RequestParam("desde") LocalDateTime desde,
                                      @RequestParam("hasta") LocalDateTime hasta,
                                      Model model) {
        List<ServicioDTO> servicios = servicioServicio.traerServiciosPorPresencialYFechas(presencial, desde, hasta);
        model.addAttribute("servicios", servicios);
        return "resultado-servicios";
    }

    @GetMapping("/buscar-por-nombre-cliente")
    public String buscarPorNombreCliente(@RequestParam("nombre") String nombre,
                                         @RequestParam("desde") LocalDateTime desde,
                                         @RequestParam("hasta") LocalDateTime hasta,
                                         Model model) {
        List<ServicioDTO> servicios = servicioServicio.traerServiciosPorNombreClienteYFechas(nombre, desde, hasta);
        model.addAttribute("servicios", servicios);
        return "resultado-servicios";
    }

    @GetMapping("/buscar-por-rol-empleado")
    public String buscarPorRolEmpleado(@RequestParam("rol") String rol,
                                       @RequestParam("desde") LocalDateTime desde,
                                       @RequestParam("hasta") LocalDateTime hasta,
                                       Model model) {
        List<ServicioDTO> servicios = servicioServicio.traerServiciosPorRolEmpleadoYFechas(rol, desde, hasta);
        model.addAttribute("servicios", servicios);
        return "resultado-servicios";
    }

    @GetMapping("/buscar-por-direccion-lugar")
    public String buscarPorDireccionLugar(@RequestParam("direccion") String direccion,
                                          @RequestParam("desde") LocalDateTime desde,
                                          @RequestParam("hasta") LocalDateTime hasta,
                                          Model model) {
        List<ServicioDTO> servicios = servicioServicio.traerServiciosPorDireccionLugarYFechas(direccion, desde, hasta);
        model.addAttribute("servicios", servicios);
        return "resultado-servicios";
    }
    
    @GetMapping("/servicios-presenciales")
    public String mostrarServiciosPresenciales(Model model) {
        List<ServicioDTO> servicios = servicioServicio.obtenerServiciosPresenciales(true);
        model.addAttribute("servicios", servicios);
        return "resultado-servicios";
    }
    
    @GetMapping("/buscar-por-apellido-empleado")
    public String buscarServiciosPorApellidoEmpleado(@RequestParam("apellido") String apellido, Model model) {
        List<ServicioDTO> servicios = servicioServicio.traerServiciosPorApellidoEmpleado(apellido);
        model.addAttribute("servicios", servicios);
        return "resultado-servicios";
    }

}