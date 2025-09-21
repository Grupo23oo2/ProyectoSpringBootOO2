package org.example.turnos.dtos;

public record ClienteDTO(
    Long idPersona,
    String nombre,    
    String apellido,
    String dni,
    Long idUsuario,
    String cuit,
    Long idContacto
) {}