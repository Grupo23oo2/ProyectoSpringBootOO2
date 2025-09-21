package org.example.turnos.dtos;

import java.time.LocalDate;

public record EmpleadoDTO(
    Long idPersona,
    String nombre,
    String apellido,
    String dni,
    Long idUsuario,   // puede ser null si la persona aún no tiene usuario
    LocalDate fechaInicio
) { }
