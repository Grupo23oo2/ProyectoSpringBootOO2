package org.example.turnos.dtos;

public record ClienteCreateDTO(
    String nombre,
    String apellido,
    String dni,
    String cuit,
    UsuarioCreateDTO usuario,
    ContactoCreateDTO contacto
) {}
