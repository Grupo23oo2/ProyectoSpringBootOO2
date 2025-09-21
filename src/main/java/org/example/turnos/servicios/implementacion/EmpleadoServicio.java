package org.example.turnos.servicios.implementacion;

import org.example.turnos.dtos.EmpleadoDTO;
import org.example.turnos.excepciones.DniEmpleadoDuplicadoException;
import org.example.turnos.excepciones.MiExcepcionPersonalizada;
import org.example.turnos.modelo.Empleado;
import org.example.turnos.modelo.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        if (empleadoRepositorio.existsByDni(dto.dni())) {
            throw new DniEmpleadoDuplicadoException("Ya existe un empleado con el DNI: " + dto.dni());
        }

        try {
            Empleado empleado = toEntity(dto);
            Empleado guardado = empleadoRepositorio.save(empleado);
            return toDTO(guardado);
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo agregar el empleado: " + e.getMessage());
        }
    }

    @Override

    public EmpleadoDTO traerEmpleadoPorDni(String dni) {
        try {
            Empleado empleado = empleadoRepositorio.findByDni(dni)
                    .orElseThrow(() -> new MiExcepcionPersonalizada("Empleado no encontrado con DNI: " + dni));
            return toDTO(empleado);
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo traer el empleado: " + e.getMessage());
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
    public EmpleadoDTO modificarEmpleadoPorDni(String dniOriginal, EmpleadoDTO dto) {
    	try {
        Empleado existente = empleadoRepositorio.findByDni(dniOriginal)
                .orElseThrow(() -> new MiExcepcionPersonalizada("Empleado no encontrado con DNI: " + dniOriginal));

        existente.setNombre(dto.nombre());
        existente.setApellido(dto.apellido());
        existente.setDni(dto.dni());
        existente.setFechaInicio(dto.fechaInicio());

        if (dto.idUsuario() != null) {
            Usuario usuario = usuarioRepositorio.findById(dto.idUsuario()).orElse(null);
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

    @Transactional
    @Override
    public void eliminarEmpleadoPorDni(String dniOriginal) {
        Empleado empleado = empleadoRepositorio.findByDni(dniOriginal)
            .orElseThrow(() -> new MiExcepcionPersonalizada("No se encontró un empleado con el DNI: " + dniOriginal));

        try {
            if (empleado.getUsuario() != null) {
                usuarioRepositorio.delete(empleado.getUsuario());
                empleado.setUsuario(null);
            }

            empleadoRepositorio.delete(empleado);

        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("Error al eliminar el empleado con DNI " + dniOriginal + ": " + e.getMessage());
        }
    }

    /*private EmpleadoDTO toDTO(Empleado empleado) {
        EmpleadoDTO dto = modelMapper.map(empleado, EmpleadoDTO.class);

        // Asignamos manualmente el idUsuario
        dto.idUsuario(empleado.getUsuario() != null ? empleado.getUsuario().getIdUsuario() : null);

        return dto;
    }*/
    
    //MODIFICADO PARA RECORD

    private EmpleadoDTO toDTO(Empleado empleado) {
        return new EmpleadoDTO(
            empleado.getIdPersona(),
            empleado.getNombre(),
            empleado.getApellido(),
            empleado.getDni(),
            empleado.getUsuario() != null ? empleado.getUsuario().getIdUsuario() : null,
            empleado.getFechaInicio()
        );
    }
    
    private Empleado toEntity(EmpleadoDTO dto) {
        Empleado empleado = modelMapper.map(dto, Empleado.class);

        if (dto.idUsuario() != null) {
            Usuario usuario = usuarioRepositorio.findById(dto.idUsuario()).orElse(null);
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