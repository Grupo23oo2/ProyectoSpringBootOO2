package servicios;

import dtos.UsuarioDTO;

import java.util.List;

public interface IUsuarioServicio {
    UsuarioDTO agregarUsuario(UsuarioDTO usuarioDTO);
    List<UsuarioDTO> traerUsuarios();
    UsuarioDTO traerUsuarioPorId(Long id);
    UsuarioDTO modificarUsuario(Long id, UsuarioDTO usuarioDTO);
    void eliminarUsuario(Long id);
}
