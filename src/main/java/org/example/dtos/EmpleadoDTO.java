package org.example.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class EmpleadoDTO {

    @Getter @Setter
    private Long idPersona;

    @Getter @Setter
    private String nombre;

    @Getter @Setter
    private String apellido;

    @Getter @Setter
    private String dni;

    @Getter @Setter
    private Long idUsuario; // puede ser null si la persona aún no tiene usuario

    @Getter @Setter
    private LocalDate fechaInicio;

    public EmpleadoDTO() {}

    public EmpleadoDTO(Long idPersona, String nombre, String apellido, String dni, Long idUsuario, LocalDate fechaInicio) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.idUsuario = idUsuario;
        this.fechaInicio = fechaInicio;
    }

}
