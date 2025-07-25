package org.example.turnos.servicios;

import org.example.turnos.dtos.UsuarioDTO;

import java.util.List;

public interface IUsuarioServicio {
    UsuarioDTO agregarUsuario(UsuarioDTO usuarioDTO);
    List<UsuarioDTO> traerUsuarios();

    UsuarioDTO traerUsuarioPorEmail(String email);
    UsuarioDTO modificarUsuario(Long id, UsuarioDTO usuarioDTO);
    void eliminarUsuario(Long id);
    
   

}
