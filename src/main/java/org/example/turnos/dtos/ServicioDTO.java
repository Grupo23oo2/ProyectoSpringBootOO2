package org.example.turnos.dtos;

public class ServicioDTO {
	private Long idServicio;
	private String descripcion;
	private Long duracion;
	private boolean activo;
	
	public ServicioDTO(){}
	
	public ServicioDTO(Long idServicio, String descripcion, Long duracion, boolean activo) {
		super();
		this.idServicio = idServicio;
		this.descripcion = descripcion;
		this.duracion = duracion;
		this.activo = activo;
	}
	
	public Long getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(Long idServicio) {
		this.idServicio = idServicio;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getDuracion() {
		return duracion;
	}
	public void setDuracion(Long duracion) {
		this.duracion = duracion;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
