package org.example.turnos.servicios;

import org.example.turnos.dtos.EmpleadoDTO;
import org.example.turnos.dtos.EmpleadoUsuarioDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IEmpleadoServicio {
    public EmpleadoDTO agregarEmpleado(EmpleadoDTO dto);
    
    public Map<String, Object> altaEmpleadoYUsuario(EmpleadoUsuarioDTO dto);

    public EmpleadoDTO traerEmpleadoPorDni(String dni);

    public List<EmpleadoDTO> traerEmpleados();
    EmpleadoDTO modificarEmpleadoPorDni(String dniOriginal, EmpleadoDTO dto);
    
    void eliminarEmpleadoPorDni(String dni);
    
    public List<EmpleadoDTO> empleadosPorRol(String rol);
    public List<EmpleadoDTO> empleadosPorFechaInicio(LocalDate fecha);
}
