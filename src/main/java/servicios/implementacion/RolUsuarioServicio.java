package servicios.implementacion;

import dtos.RolUsuarioDTO;
import modelo.RolUsuario;
import modelo.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import repositorios.IRolUsuarioRepositorio;
import repositorios.IUsuarioRepositorio;
import servicios.IRolUsuarioServicio;

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
        RolUsuario entity = toEntity(dto);
        RolUsuario saved = rolUsuarioRepositorio.save(entity);
        return toDTO(saved);
    }

    @Override
    public RolUsuarioDTO traerRolUsuario(Long id) {
        RolUsuario entity = rolUsuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("RolUsuario no encontrado con id " + id));
        return toDTO(entity);
    }

    @Override
    public List<RolUsuarioDTO> traerRolesUsuarios() {
        return rolUsuarioRepositorio.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RolUsuarioDTO modificarRolUsuario(Long id, RolUsuarioDTO dto) {
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
    }

    @Override
    public void eliminarRolUsuario(Long id) {
        if (!rolUsuarioRepositorio.existsById(id)) {
            throw new RuntimeException("RolUsuario no encontrado con id " + id);
        }
        rolUsuarioRepositorio.deleteById(id);
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
