package org.example.turnos.dtos;

import java.time.LocalDateTime;

public class TurnoDTO {

    private Long idTurno;
    private boolean presencial;
    private Long idLugarTurno;
    private Long idEmpleado;
    private Long idCliente;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private Long idServicio;
    
    public TurnoDTO() {
    }

	public TurnoDTO(Long idTurno, boolean presencial, Long idLugarTurno, Long idEmpleado, Long idCliente,
			LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, Long idServicio) {
		super();
		this.idTurno = idTurno;
		this.presencial = presencial;
		this.idLugarTurno = idLugarTurno;
		this.idEmpleado = idEmpleado;
		this.idCliente = idCliente;
		this.fechaHoraInicio = fechaHoraInicio;
		this.fechaHoraFin = fechaHoraFin;
		this.idServicio = idServicio;
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

	public Long getIdLugarTurno() {
		return idLugarTurno;
	}

	public void setIdLugarTurno(Long idLugarTurno) {
		this.idLugarTurno = idLugarTurno;
	}

	public Long getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public LocalDateTime getFechaHoraInicio() {
		return fechaHoraInicio;
	}

	public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
		this.fechaHoraInicio = fechaHoraInicio;
	}

	public LocalDateTime getFechaHoraFin() {
		return fechaHoraFin;
	}

	public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
		this.fechaHoraFin = fechaHoraFin;
	}

	public Long getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(Long idServicio) {
		this.idServicio = idServicio;
	}
}

