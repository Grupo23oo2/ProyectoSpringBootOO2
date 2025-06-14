package org.example.turnos.servicios.implementacion;

import org.example.turnos.dtos.EmpleadoDTO;
import org.example.turnos.excepciones.MiExcepcionPersonalizada;
import org.example.turnos.modelo.Empleado;
import org.example.turnos.modelo.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.turnos.repositorios.IEmpleadoRepositorio;
import org.example.turnos.repositorios.IUsuarioRepositorio;
import org.example.turnos.servicios.IEmpleadoServicio;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpleadoServicio implements IEmpleadoServicio {

    @Autowired
    private IEmpleadoRepositorio empleadoRepositorio;

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public EmpleadoDTO agregarEmpleado(EmpleadoDTO dto) {
    	try {
        Empleado empleado = toEntity(dto);
        Empleado guardado = empleadoRepositorio.save(empleado);
        return toDTO(guardado);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo agregar el empleado" + e.getMessage());
        }
    }

    @Override
    public EmpleadoDTO traerEmpleadoPorId(Long id) {
    	try {
        Empleado empleado = empleadoRepositorio.findById(id)
                .orElseThrow(() -> new MiExcepcionPersonalizada("Empleado no encontrado con ID: " + id));
        return toDTO(empleado);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer el empleado" + e.getMessage());
        }
    }

    @Override
    public List<EmpleadoDTO> traerEmpleados() {
    	try {
        return empleadoRepositorio.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer los empleados" + e.getMessage());
        }
    }

    @Override
    public EmpleadoDTO modificarEmpleado(Long id, EmpleadoDTO dto) {
    	try {
        Empleado existente = empleadoRepositorio.findById(id)
                .orElseThrow(() -> new MiExcepcionPersonalizada("Empleado no encontrado con ID: " + id));

        existente.setNombre(dto.getNombre());
        existente.setApellido(dto.getApellido());
        existente.setDni(dto.getDni());
        existente.setFechaInicio(dto.getFechaInicio());

        if (dto.getIdUsuario() != null) {
            Usuario usuario = usuarioRepositorio.findById(dto.getIdUsuario()).orElse(null);
            existente.setUsuario(usuario);
        } else {
            existente.setUsuario(null);
        }

        Empleado actualizado = empleadoRepositorio.save(existente);
        return toDTO(actualizado);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo modificar los empleados" + e.getMessage());
        }
    }

    @Override
    public void eliminarEmpleado(Long id) {
    	try {
        empleadoRepositorio.deleteById(id);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo eliminar el empleado" + e.getMessage());
        }
    }

    private EmpleadoDTO toDTO(Empleado empleado) {
        EmpleadoDTO dto = modelMapper.map(empleado, EmpleadoDTO.class);

        // Asignamos manualmente el idUsuario
        dto.setIdUsuario(empleado.getUsuario() != null ? empleado.getUsuario().getIdUsuario() : null);

        return dto;
    }

    private Empleado toEntity(EmpleadoDTO dto) {
        Empleado empleado = modelMapper.map(dto, Empleado.class);

        if (dto.getIdUsuario() != null) {
            Usuario usuario = usuarioRepositorio.findById(dto.getIdUsuario()).orElse(null);
            empleado.setUsuario(usuario);
        }

        return empleado;
    }
    
    @Override
    public List<EmpleadoDTO> empleadosPorRol(String rol) {
    	try {
        return empleadoRepositorio.findByRol(rol)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer empleados por rol" + e.getMessage());
        }
    }
    
    @Override
    public List<EmpleadoDTO> empleadosPorFechaInicio(LocalDate fecha) {
    	try {
        return empleadoRepositorio.findByFechaInicio(fecha)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer empleados por fecha de inicio" + e.getMessage());
        }
    }
}