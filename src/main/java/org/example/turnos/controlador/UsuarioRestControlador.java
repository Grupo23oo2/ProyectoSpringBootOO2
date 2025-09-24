package org.example.turnos.controlador;

import org.example.turnos.dtos.UsuarioDTO;
import org.example.turnos.servicios.IUsuarioServicio;
import org.example.turnos.excepciones.MiExcepcionPersonalizada;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestControlador {

    private final IUsuarioServicio usuarioServicio;

    public UsuarioRestControlador(IUsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    // Listar todos los usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioServicio.traerUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // Buscar usuario por email
    @GetMapping("/buscar")
    public ResponseEntity<UsuarioDTO> buscarPorEmail(@RequestParam String email) {
        UsuarioDTO usuario = usuarioServicio.traerUsuarioPorEmail(email);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    // Agregar usuario
    @PostMapping
    public ResponseEntity<UsuarioDTO> agregarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO agregado = usuarioServicio.agregarUsuario(usuarioDTO);
            return ResponseEntity.ok(agregado);
        } catch (MiExcepcionPersonalizada e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Modificar usuario
    @PutMapping("/{email}")
    public ResponseEntity<UsuarioDTO> modificarUsuario(@PathVariable String email,
                                                       @RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO actualizado = usuarioServicio.modificarUsuario(email, usuarioDTO);
            if (actualizado == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(actualizado);
        } catch (MiExcepcionPersonalizada e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Eliminar usuario
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable String email) {
        usuarioServicio.eliminarUsuario(email);
        return ResponseEntity.noContent().build();
    }
}