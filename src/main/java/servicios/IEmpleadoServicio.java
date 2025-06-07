package servicios;

import dtos.EmpleadoDTO;
import modelo.Empleado;
import repositorios.IEmpleadoRepositorio;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

public interface IEmpleadoServicio {
    public EmpleadoDTO agregarEmpleado(EmpleadoDTO dto);
    public EmpleadoDTO traerEmpleadoPorId(Long id);
    public List<EmpleadoDTO> traerEmpleados();
    public EmpleadoDTO modificarEmpleado(Long id, EmpleadoDTO dto);
    void eliminarEmpleado(Long id);
    
    public List<EmpleadoDTO> empleadosPorRol(String rol);
   
    public List<EmpleadoDTO> empleadosPorFechaInicio(LocalDate fecha);
}
