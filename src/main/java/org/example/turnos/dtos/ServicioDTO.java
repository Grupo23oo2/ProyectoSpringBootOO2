package org.example.turnos.dtos;

public record ServicioDTO(
    Long idServicio,
    String descripcion,
    Long duracion,
    boolean activo
) {}