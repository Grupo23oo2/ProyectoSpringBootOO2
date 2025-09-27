package org.example.turnos.dtos;

public record ContactoCreateDTO(
    String direccion,
    String email,
    String telefono
) {}
