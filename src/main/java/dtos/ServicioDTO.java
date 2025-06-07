package dtos;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class ServicioDTO {

    @Getter @Setter
    private Long idServicio;

    @Getter @Setter
    private boolean presencial;

    @Getter @Setter
    private Long idLugarServicio;

    @Getter @Setter
    private Long idEmpleado;

    @Getter @Setter
    private Long idCliente;

    @Getter @Setter
    private LocalDateTime fechaHoraInicio;

    @Getter @Setter
    private LocalDateTime fechaHoraFin;

    public ServicioDTO() {
    }

    public ServicioDTO(Long idServicio, boolean presencial, Long idLugarServicio, Long idEmpleado, Long idCliente,
                       LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin) {
        this.idServicio = idServicio;
        this.presencial = presencial;
        this.idLugarServicio = idLugarServicio;
        this.idEmpleado = idEmpleado;
        this.idCliente = idCliente;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
    }

	public Long getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(Long idServicio) {
		this.idServicio = idServicio;
	}

	public boolean isPresencial() {
		return presencial;
	}

	public void setPresencial(boolean presencial) {
		this.presencial = presencial;
	}

	public Long getIdLugarServicio() {
		return idLugarServicio;
	}

	public void setIdLugarServicio(Long idLugarServicio) {
		this.idLugarServicio = idLugarServicio;
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
    
    
}

