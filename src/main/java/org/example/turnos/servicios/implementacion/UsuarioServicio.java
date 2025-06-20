package org.example.turnos.servicios.implementacion;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.example.turnos.dtos.RolUsuarioDTO;
import org.example.turnos.dtos.UsuarioDTO;
import org.example.turnos.excepciones.MiExcepcionPersonalizada;
import org.example.turnos.modelo.Cliente;
import org.example.turnos.modelo.Empleado;
import org.example.turnos.modelo.Persona;
import org.example.turnos.modelo.Usuario;
import org.example.turnos.repositorios.IClienteRepositorio;
import org.example.turnos.repositorios.IEmpleadoRepositorio;
import org.example.turnos.repositorios.IRolUsuarioRepositorio;
import org.example.turnos.repositorios.IUsuarioRepositorio;
import org.example.turnos.servicios.IUsuarioServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio implements IUsuarioServicio {

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;

    @Autowired
    private IEmpleadoRepositorio empleadoRepositorio;

    @Autowired
    private IClienteRepositorio clienteRepositorio;

    @Autowired
    private IRolUsuarioRepositorio rolUsuarioRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UsuarioDTO agregarUsuario(UsuarioDTO dto) {
        try {
            Persona persona = buscarPersonaPorId(dto.getIdPersona());

            Usuario usuario = toEntity(dto, persona);
            Usuario usuarioGuardado = usuarioRepositorio.save(usuario);
            return toDTO(usuarioGuardado);
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo agregar el usuario: " + e.getMessage());
        }
    }

    private Persona buscarPersonaPorId(Long idPersona) {
        try {
            Optional<Empleado> empleadoOpt = empleadoRepositorio.findById(idPersona);
            if (empleadoOpt.isPresent()) return empleadoOpt.get();

            Optional<Cliente> clienteOpt = clienteRepositorio.findById(idPersona);
            if (clienteOpt.isPresent()) return clienteOpt.get();

            throw new MiExcepcionPersonalizada("No se encontró Persona con ID: " + idPersona);
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo traer la persona por id: " + e.getMessage());
        }
    }

    @Override
    public List<UsuarioDTO> traerUsuarios() {
        try {
            return usuarioRepositorio.findAll()
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo traer los usuarios: " + e.getMessage());
        }
    }

    @Override
    public UsuarioDTO traerUsuarioPorId(Long id) {
        try {
            Usuario usuario = usuarioRepositorio.findById(id)
                    .orElseThrow(() -> new MiExcepcionPersonalizada("Usuario no encontrado con ID: " + id));
            return toDTO(usuario);
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo traer los usuarios por id: " + e.getMessage());
        }
    }

    @Override
    public UsuarioDTO modificarUsuario(Long id, UsuarioDTO dto) {
        try {
            Usuario usuarioExistente = usuarioRepositorio.findById(id)
                    .orElseThrow(() -> new MiExcepcionPersonalizada("Usuario no encontrado con ID: " + id));

            if (dto.getIdPersona() != null) {
                Persona persona = buscarPersonaPorId(dto.getIdPersona());
                usuarioExistente.setPersona(persona);
            }

            usuarioExistente.setNombreUsuario(dto.getNombreUsuario());
            usuarioExistente.setContraseniaUsuario(dto.getContraseniaUsuario());
            usuarioExistente.setEstado(dto.isEstado());
            usuarioExistente.setEmail(dto.getEmail());

            Usuario usuarioModificado = usuarioRepositorio.save(usuarioExistente);
            return toDTO(usuarioModificado);
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo modificar los usuarios: " + e.getMessage());
        }
    }

    @Override
    public void eliminarUsuario(Long id) {
        try {
            Usuario usuario = usuarioRepositorio.findById(id)
                    .orElseThrow(() -> new MiExcepcionPersonalizada("No se encontró Usuario con ID: " + id));
            usuarioRepositorio.delete(usuario);
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo eliminar el usuario: " + e.getMessage());
        }
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);

        if (usuario.getPersona() != null) {
            dto.setIdPersona(usuario.getPersona().getIdPersona());
        }

        return dto;
    }

    /**
     * Mapeo manual de DTO a entidad para evitar problemas con Persona abstracta
     */
    private Usuario toEntity(UsuarioDTO dto, Persona persona) {
        Usuario usuario = new Usuario();

        if (dto.getIdUsuario() != null) {
            usuario.setIdUsuario(dto.getIdUsuario());
        }
        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setContraseniaUsuario(dto.getContraseniaUsuario());
        usuario.setEstado(dto.isEstado());
        usuario.setEmail(dto.getEmail());

        usuario.setPersona(persona);

        return usuario;
    }

    @Override
    public List<RolUsuarioDTO> obtenerRolesUsuariosPorUsuario(Long idUsuario) {
        try {
            return usuarioRepositorio.findRolesUsuarioByUsuario(idUsuario)
                    .stream()
                    .map(rol -> modelMapper.map(rol, RolUsuarioDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new MiExcepcionPersonalizada("No se pudo traer la lista de roles por usuario: " + e.getMessage());
        }
        
        
    }
}

