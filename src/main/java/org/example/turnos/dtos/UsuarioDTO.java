package org.example.turnos.dtos;

import java.time.LocalDateTime;

public record UsuarioDTO(
    Long idUsuario,
    String nombreUsuario,
    String contraseniaUsuario,
    boolean estado,
    Long idPersona,
    String email,
    String rol,
    LocalDateTime fechaCreacion
) {}
