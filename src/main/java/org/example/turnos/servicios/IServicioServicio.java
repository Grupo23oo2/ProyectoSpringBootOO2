package org.example.turnos.servicios;

import org.example.turnos.dtos.ServicioDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface IServicioServicio {
    ServicioDTO agregarServicio(ServicioDTO dto);
    ServicioDTO traerServicio(Long id);
    List<ServicioDTO> traerServicios();
    ServicioDTO modificarServicio(Long id, ServicioDTO dto);  // solo fechas modificables
    void eliminarServicio(Long id);

    // Consultas por fecha
    List<ServicioDTO> traerServiciosEntreFechas(LocalDateTime desde, LocalDateTime hasta);
    List<ServicioDTO> traerServiciosDeClienteEntreFechas(Long idCliente, LocalDateTime desde, LocalDateTime hasta);
    List<ServicioDTO> traerServiciosDeEmpleadoEntreFechas(Long idEmpleado, LocalDateTime desde, LocalDateTime hasta);
    List<ServicioDTO> traerServiciosPorLugarEntreFechas(Integer idLugar, LocalDateTime desde, LocalDateTime hasta);

    // Consultas por fecha + atributo
    List<ServicioDTO> traerServiciosPorPresencialYFechas(boolean presencial, LocalDateTime desde, LocalDateTime hasta);
    List<ServicioDTO> traerServiciosPorNombreClienteYFechas(String nombreCliente, LocalDateTime desde, LocalDateTime hasta);
    List<ServicioDTO> traerServiciosPorRolEmpleadoYFechas(String rolEmpleado, LocalDateTime desde, LocalDateTime hasta);
    List<ServicioDTO> traerServiciosPorDireccionLugarYFechas(String direccionLugar, LocalDateTime desde, LocalDateTime hasta);
    
    List<ServicioDTO> obtenerServiciosPresenciales(boolean presencial);
    List<ServicioDTO> traerServiciosPorApellidoEmpleado(String apellido);
}