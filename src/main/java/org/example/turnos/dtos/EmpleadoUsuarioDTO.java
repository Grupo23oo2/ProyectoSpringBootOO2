package org.example.turnos.dtos;

import java.time.LocalDate;

public record EmpleadoUsuarioDTO(
	    String nombre,
	    String apellido,
	    String dni,
	    LocalDate fechaInicio,
	    String nombreUsuario,
	    String email,
	    String contraseniaUsuario
	) {}
