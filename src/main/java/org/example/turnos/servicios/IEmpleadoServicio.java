package org.example.turnos.servicios;

import org.example.turnos.dtos.EmpleadoDTO;

import java.time.LocalDate;
import java.util.List;

public interface IEmpleadoServicio {
    public EmpleadoDTO agregarEmpleado(EmpleadoDTO dto);

    public EmpleadoDTO traerEmpleadoPorDni(String dni);

    public List<EmpleadoDTO> traerEmpleados();
    EmpleadoDTO modificarEmpleadoPorDni(String dniOriginal, EmpleadoDTO dto);
    
    void eliminarEmpleadoPorDni(String dni);
    
    public List<EmpleadoDTO> empleadosPorRol(String rol);
    public List<EmpleadoDTO> empleadosPorFechaInicio(LocalDate fecha);
}
