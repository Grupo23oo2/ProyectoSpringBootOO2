package org.example.turnos.servicios;

import org.example.turnos.dtos.RolUsuarioDTO;

import java.util.List;

public interface IRolUsuarioServicio {

    RolUsuarioDTO agregarRolUsuario(RolUsuarioDTO dto);

    RolUsuarioDTO traerRolUsuario(Long id);

    List<RolUsuarioDTO> traerRolesUsuarios();

    RolUsuarioDTO modificarRolUsuario(Long id, RolUsuarioDTO dto);

    void eliminarRolUsuario(Long id);

}
