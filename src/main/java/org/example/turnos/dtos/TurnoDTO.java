package org.example.turnos.dtos;


import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;


public class TurnoDTO {

    private Long idTurno;
    private boolean presencial;
    private String direccionLugar;
    private String dniEmpleado;
    private String cuitCliente;
    private LocalDateTime fechaHoraInicio;
    private String descripcionServicio;
    private int duracionMinutos;

    
    public TurnoDTO() {
    }

	

	public TurnoDTO(Long idTurno, boolean presencial, String direccionLugar, String dniEmpleado, String cuitCliente,
			LocalDateTime fechaHoraInicio, String descripcionServicio, int duracionMinutos) {
		super();
		this.idTurno = idTurno;
		this.presencial = presencial;
		this.direccionLugar = direccionLugar;
		this.dniEmpleado = dniEmpleado;
		this.cuitCliente = cuitCliente;
		this.fechaHoraInicio = fechaHoraInicio;
		this.descripcionServicio = descripcionServicio;
		this.duracionMinutos = duracionMinutos;
	}



	public Long getIdTurno() {
		return idTurno;
	}

	public void setIdTurno(Long idTurno) {
		this.idTurno = idTurno;
	}

	public boolean isPresencial() {
		return presencial;
	}

	public void setPresencial(boolean presencial) {
		this.presencial = presencial;
	}

	

	public String getDireccionLugar() {
		return direccionLugar;
	}



	public void setDireccionLugar(String direccionLugar) {
		this.direccionLugar = direccionLugar;
	}



	public String getDniEmpleado() {
		return dniEmpleado;
	}



	public void setDniEmpleado(String dniEmpleado) {
		this.dniEmpleado = dniEmpleado;
	}



	public String getCuitCliente() {
		return cuitCliente;
	}



	public void setCuitCliente(String cuitCliente) {
		this.cuitCliente = cuitCliente;
	}



	public String getDescripcionServicio() {
		return descripcionServicio;
	}



	public void setDescripcionServicio(String descripcionServicio) {
		this.descripcionServicio = descripcionServicio;
	}



	public LocalDateTime getFechaHoraInicio() {
		return fechaHoraInicio;
	}

	public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
		this.fechaHoraInicio = fechaHoraInicio;
	}



	public int getDuracionMinutos() {
	    return duracionMinutos;
	}

	public void setDuracionMinutos(int duracionMinutos) {
	    this.duracionMinutos = duracionMinutos;
	}
	
	public LocalDate getFecha() {
	    return fechaHoraInicio != null ? fechaHoraInicio.toLocalDate() : null;
	}

	public LocalTime getHora() {
	    return fechaHoraInicio != null ? fechaHoraInicio.toLocalTime() : null;
	}

}

