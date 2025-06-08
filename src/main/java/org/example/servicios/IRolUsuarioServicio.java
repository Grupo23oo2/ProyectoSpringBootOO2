package org.example.servicios;

import org.example.dtos.RolUsuarioDTO;

import java.util.List;

public interface IRolUsuarioServicio {

    RolUsuarioDTO agregarRolUsuario(RolUsuarioDTO dto);

    RolUsuarioDTO traerRolUsuario(Long id);

    List<RolUsuarioDTO> traerRolesUsuarios();

    RolUsuarioDTO modificarRolUsuario(Long id, RolUsuarioDTO dto);

    void eliminarRolUsuario(Long id);

}
