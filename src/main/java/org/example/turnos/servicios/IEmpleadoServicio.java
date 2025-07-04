package org.example.turnos.servicios;

import org.example.turnos.dtos.EmpleadoDTO;

import java.time.LocalDate;
import java.util.List;

public interface IEmpleadoServicio {
    public EmpleadoDTO agregarEmpleado(EmpleadoDTO dto);
    public EmpleadoDTO traerEmpleadoPorId(Long id);
    public List<EmpleadoDTO> traerEmpleados();
    public EmpleadoDTO modificarEmpleado(Long id, EmpleadoDTO dto);
    void eliminarEmpleado(Long id);
    
    public List<EmpleadoDTO> empleadosPorRol(String rol);
    public List<EmpleadoDTO> empleadosPorFechaInicio(LocalDate fecha);
}
