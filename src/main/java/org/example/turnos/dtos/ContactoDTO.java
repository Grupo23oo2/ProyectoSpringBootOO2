package org.example.turnos.dtos;

public record ContactoDTO(
    Long idContacto,
    String direccion,
    String email,
    String telefono
) {}