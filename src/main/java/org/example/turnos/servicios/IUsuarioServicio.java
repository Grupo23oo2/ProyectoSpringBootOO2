package org.example.turnos.servicios;

import org.example.turnos.dtos.UsuarioDTO;
import org.example.turnos.modelo.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioServicio {
    UsuarioDTO agregarUsuario(UsuarioDTO usuarioDTO);
    List<UsuarioDTO> traerUsuarios();

    UsuarioDTO traerUsuarioPorEmail(String email);
    UsuarioDTO modificarUsuario(String email, UsuarioDTO dto);
    void eliminarUsuario(String email);
    public Optional<UsuarioDTO> traerUsuarioPorNombreUsuario(String nombreUsuario);
}
