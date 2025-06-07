package servicios.implementacion;

import dtos.EmpleadoDTO;
import modelo.Empleado;
import modelo.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositorios.IEmpleadoRepositorio;
import repositorios.IUsuarioRepositorio;
import servicios.IEmpleadoServicio;

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
        Empleado empleado = toEntity(dto);
        Empleado guardado = empleadoRepositorio.save(empleado);
        return toDTO(guardado);
    }

    @Override
    public EmpleadoDTO traerEmpleadoPorId(Long id) {
        Empleado empleado = empleadoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));
        return toDTO(empleado);
    }

    @Override
    public List<EmpleadoDTO> traerEmpleados() {
        return empleadoRepositorio.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmpleadoDTO modificarEmpleado(Long id, EmpleadoDTO dto) {
        Empleado existente = empleadoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));

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
    }

    @Override
    public void eliminarEmpleado(Long id) {
        empleadoRepositorio.deleteById(id);
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
    
    //metodos personalizadso
    @Override
    public List<EmpleadoDTO> empleadosPorRol(String rol) {
        return empleadoRepositorio.findByRol(rol)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<EmpleadoDTO> empleadosPorFechaInicio(LocalDate fecha) {
        return empleadoRepositorio.findByFechaInicio(fecha)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}