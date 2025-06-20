package org.example.turnos.servicios.implementacion;

import org.example.turnos.dtos.RolUsuarioDTO;
import org.example.turnos.excepciones.MiExcepcionPersonalizada;
import org.example.turnos.modelo.RolUsuario;
import org.example.turnos.modelo.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.turnos.repositorios.IRolUsuarioRepositorio;
import org.example.turnos.repositorios.IUsuarioRepositorio;
import org.example.turnos.servicios.IRolUsuarioServicio;

import java.util.List;
import java.util.stream.Collectors;

@Service
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
            throw new MiExcepcionPersonalizada("No se pudo agregar el Rol" + e.getMessage());
        }
    }



    @Override
    public RolUsuarioDTO traerRolUsuario(Long id) {
        try {
            RolUsuario entity = rolUsuarioRepositorio.findById(id)
                    .orElseThrow(() -> new MiExcepcionPersonalizada("RolUsuario no encontrado con id " + id));
            return toDTO(entity);
        } catch (Exception e) {
            e.printStackTrace(); // 👈 Imprime el error real en consola
            throw new MiExcepcionPersonalizada("Error al buscar el Rol: " + e.getMessage());
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
            throw new MiExcepcionPersonalizada("No se pudo traer la lista de Roles" + e.getMessage());
        }
    }

    @Override
    public RolUsuarioDTO modificarRolUsuario(Long id, RolUsuarioDTO dto) {
    	try {
        RolUsuario entity = rolUsuarioRepositorio.findById(id)
                .orElseThrow(() -> new MiExcepcionPersonalizada("RolUsuario no encontrado con id " + id));

        entity.setRole(dto.getRole());

        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepositorio.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new MiExcepcionPersonalizada("Usuario no encontrado con id " + dto.getUsuarioId()));
            entity.setUsuario(usuario);
        }

        RolUsuario updated = rolUsuarioRepositorio.save(entity);
        return toDTO(updated);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("El Rol no pudo ser modificado" + e.getMessage());
        }
    }

    @Override
    public void eliminarRolUsuario(Long id) {
    	try {
        if (!rolUsuarioRepositorio.existsById(id)) {
            throw new MiExcepcionPersonalizada("RolUsuario no encontrado con id " + id);
        }
        rolUsuarioRepositorio.deleteById(id);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("El Rol no pudo ser eliminado" + e.getMessage());
        }
    }



    // Conversión a DTO
    private RolUsuarioDTO toDTO(RolUsuario entity) {
        RolUsuarioDTO dto = modelMapper.map(entity, RolUsuarioDTO.class);
        if (entity.getUsuario() != null) {
            dto.setUsuarioId(entity.getUsuario().getIdUsuario());
        }
        return dto;
    }

    private RolUsuario toEntity(RolUsuarioDTO dto) {
        RolUsuario entity = modelMapper.map(dto, RolUsuario.class);

        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepositorio.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new MiExcepcionPersonalizada("Usuario no encontrado con id " + dto.getUsuarioId()));
            entity.setUsuario(usuario);
        }
        return entity;
    }

}
