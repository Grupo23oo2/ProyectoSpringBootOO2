package org.example.turnos.dtos;

import java.time.LocalDate;

public class EmpleadoDTO {

    private Long idPersona;
    private String nombre;
    private String apellido;
    private String dni;
    private Long idUsuario; // puede ser null si la persona aún no tiene usuario
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

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
    
    

}
