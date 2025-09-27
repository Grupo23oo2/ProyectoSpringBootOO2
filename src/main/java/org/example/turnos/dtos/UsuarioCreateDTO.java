package org.example.turnos.dtos;

public record UsuarioCreateDTO(
    String email,
    String nombreUsuario,
    String contraseniaUsuario
) {}
