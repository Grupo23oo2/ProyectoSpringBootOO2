package org.example.servicios;

import org.example.dtos.EmpleadoDTO;

import java.time.LocalDate;
import java.util.List;

public interface IEmpleadoServicio {
    public EmpleadoDTO agregarEmpleado(EmpleadoDTO dto);
    public EmpleadoDTO traerEmpleadoPorId(Long id);
    public List<EmpleadoDTO> traerEmpleados();
    public EmpleadoDTO modificarEmpleado(Long id, EmpleadoDTO dto);
    void eliminarEmpleado(Long id);
}
