package org.example.turnos.servicios.implementacion;

import org.example.turnos.dtos.EmpleadoDTO;
import org.example.turnos.dtos.EmpleadoUsuarioDTO;
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
import org.example.turnos.servicios.IUsuarioServicio;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmpleadoServicio implements IEmpleadoServicio {

    @Autowired
    private IEmpleadoRepositorio empleadoRepositorio;

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;  

    @Autowired
    private EmailServicio emailServicio;

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
    
    @Transactional
    public Map<String, Object> altaEmpleadoYUsuario(EmpleadoUsuarioDTO dto) {

        // 1. Crear el empleado
        Empleado empleado = new Empleado();
        empleado.setNombre(dto.nombre());
        empleado.setApellido(dto.apellido());
        empleado.setDni(dto.dni());
        empleado.setFechaInicio(dto.fechaInicio());

        // 2. Crear el usuario y vincularlo al empleado
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(dto.nombreUsuario());
        usuario.setContraseniaUsuario(passwordEncoder.encode(dto.contraseniaUsuario()));
        usuario.setEmail(dto.email());
        usuario.setEstado(true);
        usuario.setRol("ROLE_EMPLEADO");
        usuario.setPersona(empleado);       // vínculo
        empleado.setUsuario(usuario);       // vínculo inverso

        // 3. Persistir el empleado (cascade guarda al usuario automáticamente)
        Empleado guardado = empleadoRepositorio.save(empleado);
        Usuario usuarioGuardado = guardado.getUsuario(); // <-- usuario guardado con id generado

        // 4. Opcional: enviar correo
        String contenidoHtml = "<html><body><h1>Alta Exitosa</h1></body></html>";
        emailServicio.enviarCorreoHtml(usuarioGuardado.getEmail(), "Alta de usuario", contenidoHtml);

        // 5. Retornar datos usando los DTO mapeados
        Map<String, Object> response = new HashMap<>();
        response.put("empleado", toDTO(guardado));
        response.put("usuario", usuarioServicio.toDTO(usuarioGuardado)); // <-- usamos el usuario guardado

        return response;
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

    @Transactional
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

  //      Empleado actualizado = empleadoRepositorio.save(existente);
        return toDTO(existente); //antes estaba (actualizado)
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
        Empleado empleado = new Empleado();
        empleado.setIdPersona(dto.idPersona());
        empleado.setNombre(dto.nombre());
        empleado.setApellido(dto.apellido());
        empleado.setDni(dto.dni());
        empleado.setFechaInicio(dto.fechaInicio());

        if (dto.idUsuario() != null) {
            Usuario usuario = usuarioRepositorio.findById(dto.idUsuario()).orElse(null);
            empleado.setUsuario(usuario);
        }

        return empleado;
    }
    
    
    
    
    
    /*
    
    private Empleado toEntity(EmpleadoDTO dto) {
        Empleado empleado = modelMapper.map(dto, Empleado.class);

        if (dto.idUsuario() != null) {
            Usuario usuario = usuarioRepositorio.findById(dto.idUsuario()).orElse(null);
            empleado.setUsuario(usuario);
        }

        return empleado;
    }*/
    
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