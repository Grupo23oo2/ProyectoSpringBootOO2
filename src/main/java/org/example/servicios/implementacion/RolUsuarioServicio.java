package org.example.servicios.implementacion;

import org.example.dtos.RolUsuarioDTO;
import org.example.excepciones.MiExcepcionPersonalizada;
import org.example.modelo.RolUsuario;
import org.example.modelo.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.repositorios.IRolUsuarioRepositorio;
import org.example.repositorios.IUsuarioRepositorio;
import org.example.servicios.IRolUsuarioServicio;

import java.util.List;
import java.util.stream.Collectors;

public class RolUsuarioServicio implements IRolUsuarioServicio {

    @Autowired
    private IRolUsuarioRepositorio rolUsuarioRepositorio;

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public RolUsuarioDTO agregarRolUsuario(RolUsuarioDTO dto) {
        try {
            RolUsuario entity = toEntity(dto);
            RolUsuario saved = rolUsuarioRepositorio.save(entity);
            return toDTO(saved);
        } catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo agregar el Rol");
        }
    }



    @Override
    public RolUsuarioDTO traerRolUsuario(Long id) {
    	try {
        RolUsuario entity = rolUsuarioRepositorio.findById(id)
                .orElseThrow(() -> new MiExcepcionPersonalizada("RolUsuario no encontrado con id " + id));
        return toDTO(entity);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se encuentra el Rol");
        }
    }

    @Override
    public List<RolUsuarioDTO> traerRolesUsuarios() {
    	try {
        return rolUsuarioRepositorio.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo traer la lista de Roles");
        }
    }

    @Override
    public RolUsuarioDTO modificarRolUsuario(Long id, RolUsuarioDTO dto) {
    	try {
        RolUsuario entity = rolUsuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("RolUsuario no encontrado con id " + id));

        entity.setRole(dto.getRole());

        if (dto.getIdUsuario() != null) {
            Usuario usuario = usuarioRepositorio.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + dto.getIdUsuario()));
            entity.setUsuario(usuario);
        }

        RolUsuario updated = rolUsuarioRepositorio.save(entity);
        return toDTO(updated);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("El Rol no pudo ser modificado");
        }
    }

    @Override
    public void eliminarRolUsuario(Long id) {
    	try {
        if (!rolUsuarioRepositorio.existsById(id)) {
            throw new RuntimeException("RolUsuario no encontrado con id " + id);
        }
        rolUsuarioRepositorio.deleteById(id);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("El Rol no pudo ser eliminado");
        }
    }



    // Conversión a DTO
    private RolUsuarioDTO toDTO(RolUsuario entity) {
        RolUsuarioDTO dto = modelMapper.map(entity, RolUsuarioDTO.class);
        if (entity.getUsuario() != null) {
            dto.setIdUsuario(entity.getUsuario().getIdUsuario());
        }
        return dto;
    }

    private RolUsuario toEntity(RolUsuarioDTO dto) {
        RolUsuario entity = modelMapper.map(dto, RolUsuario.class);

        if (dto.getIdUsuario() != null) {
            Usuario usuario = usuarioRepositorio.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + dto.getIdUsuario()));
            entity.setUsuario(usuario);
        }
        return entity;
    }

}
