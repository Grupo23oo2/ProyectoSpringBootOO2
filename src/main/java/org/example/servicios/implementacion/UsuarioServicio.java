package org.example.servicios.implementacion;

import org.example.dtos.RolUsuarioDTO;
import org.example.dtos.UsuarioDTO;
import jakarta.persistence.EntityNotFoundException;
import org.example.modelo.Cliente;
import org.example.modelo.Empleado;
import org.example.modelo.Persona;
import org.example.modelo.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.repositorios.IClienteRepositorio;
import org.example.repositorios.IEmpleadoRepositorio;
import org.example.repositorios.IRolUsuarioRepositorio;
import org.example.repositorios.IUsuarioRepositorio;
import org.example.servicios.IUsuarioServicio;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Persona persona = buscarPersonaPorId(dto.getIdPersona());

        Usuario usuario = toEntity(dto);
        Usuario usuarioGuardado = usuarioRepositorio.save(usuario);
        return toDTO(usuarioGuardado);
    }

    private Persona buscarPersonaPorId(Long idPersona) {
        Optional<Empleado> empleadoOpt = empleadoRepositorio.findById(idPersona);
        if (empleadoOpt.isPresent()) return empleadoOpt.get();

        Optional<Cliente> clienteOpt = clienteRepositorio.findById(idPersona);
        if (clienteOpt.isPresent()) return clienteOpt.get();

        throw new EntityNotFoundException("No se encontró Persona con ID: " + idPersona);
    }

    @Override
    public List<UsuarioDTO> traerUsuarios() {
        return usuarioRepositorio.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO traerUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));
        return toDTO(usuario);
    }

    @Override
    public UsuarioDTO modificarUsuario(Long id, UsuarioDTO dto) {
        Usuario usuarioExistente = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));

        Persona persona = buscarPersonaPorId(dto.getIdPersona());

        usuarioExistente.setNombreUsuario(dto.getNombreUsuario());
        usuarioExistente.setContraseniaUsuario(dto.getContraseniaUsuario());
        usuarioExistente.setPersona(persona);

        Usuario usuarioModificado = usuarioRepositorio.save(usuarioExistente);
        return toDTO(usuarioModificado);
    }

    @Override
    public void eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró Usuario con ID: " + id));
        usuarioRepositorio.delete(usuario);
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);
        if (usuario.getPersona() != null) {
            dto.setIdPersona(usuario.getPersona().getIdPersona());
        }
        // Si querés, podés mapear roles a List<String> en dto también, con un mapping personalizado
        return dto;
    }

    private Usuario toEntity(UsuarioDTO dto) {
        Usuario usuario = modelMapper.map(dto, Usuario.class);

        if (dto.getIdPersona() != null) {
            Persona persona = buscarPersonaPorId(dto.getIdPersona());
            usuario.setPersona(persona);
        }
        return usuario;
    }
    
   @Override
    public List<RolUsuarioDTO> obtenerRolesUsuariosPorUsuario(Long idUsuario) {
        return usuarioRepositorio.findRolesUsuarioByUsuario(idUsuario)
                .stream()
                .map(rol -> modelMapper.map(rol, RolUsuarioDTO.class))
                .collect(Collectors.toList());
    }


}
