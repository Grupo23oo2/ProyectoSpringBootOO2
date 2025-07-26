package org.example.turnos.servicios;

import org.example.turnos.dtos.UsuarioDTO;

import java.util.List;

public interface IUsuarioServicio {
    UsuarioDTO agregarUsuario(UsuarioDTO usuarioDTO);
    List<UsuarioDTO> traerUsuarios();

    UsuarioDTO traerUsuarioPorEmail(String email);
    UsuarioDTO modificarUsuario(String email, UsuarioDTO dto);
    void eliminarUsuario(String email);
}
