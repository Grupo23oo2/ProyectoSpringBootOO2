package org.example.turnos.controlador;

import org.example.turnos.dtos.TurnoDTO;
import org.example.turnos.servicios.ITurnoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import java.time.*;
import java.util.ArrayList;

import java.util.List;

@Controller
@RequestMapping("/turnos")
public class TurnoControlador {

    @Autowired
    private ITurnoServicio turnoServicio;

    @GetMapping("/formulario")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLEADO')")
    public String mostrarFormulario(Model model) {
        model.addAttribute("horasDisponibles", turnoServicio.obtenerHorasDisponiblesFijas());//agregado en nueva logica de turno
        model.addAttribute("diasDisponibles", turnoServicio.obtenerDiasProximos(7));
        return "buscar-turno";

    }
    
    
 // --- ABM Turnos ---

    @GetMapping("/todos")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLEADO')")
    public String traerTodosLosTurnos(Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnos();
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }


    @PostMapping("/agregar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLEADO')")
    public String agregarTurno(@ModelAttribute TurnoDTO dto, Model model) {
        TurnoDTO agregado = turnoServicio.agregarTurno(dto);
        model.addAttribute("turno", agregado);
        return "resultado-turno";
    }

    @PostMapping("/modificar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLEADO')")
    public String modificarTurno(@RequestParam Long id, @ModelAttribute TurnoDTO dto, Model model) {
        TurnoDTO actualizado = turnoServicio.modificarTurno(id, dto);
        model.addAttribute("turno", actualizado);
        return "resultado-turno";
    }

    @PostMapping("/eliminar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLEADO')")
    public String eliminarTurno(@RequestParam Long id, Model model) {
        turnoServicio.eliminarTurno(id);
        model.addAttribute("mensaje", "Turno eliminado con éxito (ID: " + id + ")");
        return "resultado-turno";
    }

    // --- CONSULTAS POR FILTROS ---
    
    @GetMapping("/buscar-entre-fechas")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLEADO')")
    public String buscarEntreFechas(@RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
                                    @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
                                    Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnosEntreFechas(desde, hasta);
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }

    @GetMapping("/buscar-por-cliente")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('EMPLEADO')")
    public String buscarPorClienteEntreFechas(@RequestParam("cuit") String cuit,
                                              @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
                                              @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
                                              Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnosDeClientePorCuitEntreFechas(cuit, desde, hasta);

        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }



    @GetMapping("/buscar-por-empleado")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLEADO')")
    public String buscarPorEmpleadoEntreFechas(@RequestParam("dni") String dni,
                                               @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
                                               @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
                                               Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnosDeEmpleadoPorDniEntreFechas(dni, desde, hasta);
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }


    @GetMapping("/buscar-por-lugar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLEADO')")
    public String buscarPorLugarEntreFechas(@RequestParam("direccion") String direccion,
                                            @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
                                            @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
                                            Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnosPorDireccionEntreFechas(direccion, desde, hasta);
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }


    // --- CONSULTAS AVANZADAS ---

    @GetMapping("/buscar-por-presencial")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLEADO')")
    public String buscarPorPresencial(@RequestParam("presencial") boolean presencial,
                                      @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
                                      @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
                                      Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnosPorPresencialYFechas(presencial, desde, hasta);
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }

    @GetMapping("/buscar-por-nombre-cliente")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('EMPLEADO')")
    public String buscarPorNombreCliente(@RequestParam("nombre") String nombre,
                                         @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
                                         @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
                                         Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnosPorNombreClienteYFechas(nombre, desde, hasta);
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }

  

    @GetMapping("/buscar-por-direccion-lugar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLEADO')")
    public String buscarPorDireccionLugar(@RequestParam("direccion") String direccion,
                                          @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
                                          @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
                                          Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnosPorDireccionLugarYFechas(direccion, desde, hasta);
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }

    @GetMapping("/turnos-presenciales")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLEADO')")
    public String mostrarTurnosPresenciales(Model model) {
        List<TurnoDTO> turnos = turnoServicio.obtenerTurnosPresenciales(true);
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }

    @GetMapping("/buscar-por-apellido-empleado")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLEADO')")
    public String buscarTurnosPorApellidoEmpleado(@RequestParam("apellido") String apellido, Model model) {
        List<TurnoDTO> turnos = turnoServicio.traerTurnosPorApellidoEmpleado(apellido);
        model.addAttribute("turnos", turnos);
        return "resultado-turnos";
    }


    @GetMapping("/nuevo")//nueva logica turno
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLEADO')")
    public String mostrarFormularioNuevoTurno(Model model) {
        // Generamos una lista de los próximos 7 días
        List<LocalDate> diasDisponibles = new ArrayList<>();
        LocalDate hoy = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            diasDisponibles.add(hoy.plusDays(i));
        }

        // Horarios disponibles fijos (08:00 a 18:00 cada 30 minutos)
        List<String> horasDisponibles = turnoServicio.obtenerHorasDisponiblesFijas();

        model.addAttribute("dias", diasDisponibles);
        model.addAttribute("horas", horasDisponibles);
        return "home/nuevoTurno";
    }
    
    @PostMapping("/guardarNuevo")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLEADO')")
    public String guardarNuevoTurno(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam("hora") String hora,
            @RequestParam("duracion") int duracion,
            @RequestParam("idCliente") Long idCliente,
            @RequestParam("idEmpleado") Long idEmpleado,
            @RequestParam("idLugarTurno") Long idLugar,
            @RequestParam(value = "idServicio", required = false) Long idServicio,
            @RequestParam("presencial") boolean presencial,
            Model model
    ) {
        LocalDateTime fechaHoraInicio = LocalDateTime.of(fecha, LocalTime.parse(hora));

        TurnoDTO turnoDTO = new TurnoDTO();
        turnoDTO.setFechaHoraInicio(fechaHoraInicio);
        turnoDTO.setDuracionMinutos(duracion);
        turnoDTO.setIdCliente(idCliente);
        turnoDTO.setIdEmpleado(idEmpleado);
        turnoDTO.setIdLugarTurno(idLugar);
        turnoDTO.setIdServicio(idServicio);
        turnoDTO.setPresencial(presencial);

        TurnoDTO guardado = turnoServicio.agregarTurno(turnoDTO);
        model.addAttribute("turno", guardado);
        return "resultado-turno";
    }
    
    

    
}