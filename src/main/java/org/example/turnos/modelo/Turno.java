package org.example.turnos.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTurno;

    private boolean presencial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lugar_id", nullable = false)
    private Lugar lugarTurno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    @PrimaryKeyJoinColumn // le dice que comparten el mismo id
    private Servicio servicio;
    
    public Turno() {
    	
    }

	public Turno(Long idTurno, boolean presencial, Lugar lugarTurno, Empleado empleado, Cliente cliente,
			LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, Servicio servicio) {
		super();
		this.idTurno = idTurno;
		this.presencial = presencial;
		this.lugarTurno = lugarTurno;
		this.empleado = empleado;
		this.cliente = cliente;
		this.fechaHoraInicio = fechaHoraInicio;
		this.fechaHoraFin = fechaHoraFin;
		this.servicio = servicio;
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

	public Lugar getLugarTurno() {
		return lugarTurno;
	}

	public void setLugarTurno(Lugar lugarTurno) {
		this.lugarTurno = lugarTurno;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}
}
