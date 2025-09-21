package org.example.turnos.dtos;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

public record TurnoDTO(
    Long idTurno,
    boolean presencial,
    String direccionLugar,
    String dniEmpleado,
    String cuitCliente,
    LocalDateTime fechaHoraInicio,
    String descripcionServicio,
    int duracionMinutos
) {

    public LocalDate getFecha() {
        return fechaHoraInicio != null ? fechaHoraInicio.toLocalDate() : null;
    }

    public LocalTime getHora() {
        return fechaHoraInicio != null ? fechaHoraInicio.toLocalTime() : null;
    }
}