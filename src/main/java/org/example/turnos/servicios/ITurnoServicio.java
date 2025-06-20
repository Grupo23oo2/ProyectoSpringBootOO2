package org.example.turnos.servicios;

import org.example.turnos.dtos.TurnoDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ITurnoServicio {
	TurnoDTO agregarTurno(TurnoDTO dto);
	TurnoDTO traerTurno(Long id);
    List<TurnoDTO> traerTurnos();
    TurnoDTO modificarTurno(Long id, TurnoDTO dto);  // solo fechas modificables
    void eliminarTurno(Long id);

    // Consultas por fecha
    List<TurnoDTO> traerTurnosEntreFechas(LocalDateTime desde, LocalDateTime hasta);
    List<TurnoDTO> traerTurnosDeClienteEntreFechas(Long idCliente, LocalDateTime desde, LocalDateTime hasta);
    List<TurnoDTO> traerTurnosDeEmpleadoEntreFechas(Long idEmpleado, LocalDateTime desde, LocalDateTime hasta);
    List<TurnoDTO> traerTurnosPorLugarEntreFechas(Integer idLugar, LocalDateTime desde, LocalDateTime hasta);

    // Consultas por fecha + atributo
    List<TurnoDTO> traerTurnosPorPresencialYFechas(boolean presencial, LocalDateTime desde, LocalDateTime hasta);
    List<TurnoDTO> traerTurnosPorNombreClienteYFechas(String nombreCliente, LocalDateTime desde, LocalDateTime hasta);
    List<TurnoDTO> traerTurnosPorRolEmpleadoYFechas(String rolEmpleado, LocalDateTime desde, LocalDateTime hasta);
    List<TurnoDTO> traerTurnosPorDireccionLugarYFechas(String direccionLugar, LocalDateTime desde, LocalDateTime hasta);
    
    List<TurnoDTO> obtenerTurnosPresenciales(boolean presencial);
    List<TurnoDTO> traerTurnosPorApellidoEmpleado(String apellido);
}