package org.example.turnos.controlador;

import org.example.turnos.dtos.LoginDTO;
import org.example.turnos.dtos.UsuarioDTO;
import org.example.turnos.servicios.implementacion.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class LoginRestController {

    private final UsuarioServicio usuarioServicio;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginRestController(UsuarioServicio usuarioServicio, PasswordEncoder passwordEncoder) {
        this.usuarioServicio = usuarioServicio;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
    	UsuarioDTO usuarioDTO = usuarioServicio.traerUsuarioPorEmail(loginDTO.nombreUsuario());

        if (usuarioDTO == null) {
            return ResponseEntity.status(401).body("Usuario no encontrado");
        }

        if (!passwordEncoder.matches(loginDTO.contraseniaUsuario(), usuarioDTO.contraseniaUsuario())) {
            return ResponseEntity.status(401).body("Contraseña incorrecta");
        }

        if (!usuarioDTO.estado()) {
            return ResponseEntity.status(403).body("Usuario inactivo");
        }

        // Login exitoso: devolvemos un OK con info básica (podés agregar JWT aquí si querés)
        return ResponseEntity.ok(
                String.format("Login exitoso! Usuario: %s, Rol: %s", 
                              usuarioDTO.nombreUsuario(), 
                              usuarioDTO.rol())
        );
    }
}
