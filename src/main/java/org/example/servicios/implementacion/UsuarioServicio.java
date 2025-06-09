package org.example.servicios.implementacion;

import org.example.dtos.RolUsuarioDTO;
import org.example.dtos.UsuarioDTO;
import org.example.excepciones.MiExcepcionPersonalizada;

import jakarta.persistence.EntityNotFoundException;
import org.example.modelo.Cliente;
import org.example.modelo.Empleado;
import org.example.modelo.Persona;
import org.example.modelo.RolUsuario;
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
import java.util.Set;
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
    	try {
        Persona persona = buscarPersonaPorId(dto.getIdPersona());

        Usuario usuario = toEntity(dto);
        Usuario usuarioGuardado = usuarioRepositorio.save(usuario);
        return toDTO(usuarioGuardado);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo agregar el usuario" + e.getMessage());
        }
    }

    private Persona buscarPersonaPorId(Long idPersona) {
    	try {
        Optional<Empleado> empleadoOpt = empleadoRepositorio.findById(idPersona);
        if (empleadoOpt.isPresent()) return empleadoOpt.get();

        Optional<Cliente> clienteOpt = clienteRepositorio.findById(idPersona);
        if (clienteOpt.isPresent()) return clienteOpt.get();

        throw new MiExcepcionPersonalizada("No se encontró Persona con ID: " + idPersona);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer la persona por id" + e.getMessage());
        }
    }

    @Override
    public List<UsuarioDTO> traerUsuarios() {
    	try {
        return usuarioRepositorio.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer los usuarios" + e.getMessage());
        }
    }

    @Override
    public UsuarioDTO traerUsuarioPorId(Long id) {
    	try {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new MiExcepcionPersonalizada("Usuario no encontrado con ID: " + id));
        return toDTO(usuario);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer los usuarios por id" + e.getMessage());
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

        Usuario usuarioModificado = usuarioRepositorio.save(usuarioExistente);
        return toDTO(usuarioModificado);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo modificar los usuarios" + e.getMessage());
        }
    }

    @Override
    public void eliminarUsuario(Long id) {
    	try {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new MiExcepcionPersonalizada("No se encontró Usuario con ID: " + id));
        usuarioRepositorio.delete(usuario);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo eliminar el usuario" + e.getMessage());
        }
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);

        if (usuario.getPersona() != null) {
            dto.setIdPersona(usuario.getPersona().getIdPersona());
        }

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
	   try {
        return usuarioRepositorio.findRolesUsuarioByUsuario(idUsuario)
                .stream()
                .map(rol -> modelMapper.map(rol, RolUsuarioDTO.class))
                .collect(Collectors.toList());
	   } catch (Exception e){
           throw new MiExcepcionPersonalizada("No se pudo traer la lista de roles por usuario" + e.getMessage());
       }
    }


}
