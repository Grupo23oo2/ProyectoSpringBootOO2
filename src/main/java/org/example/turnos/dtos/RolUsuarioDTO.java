package org.example.turnos.dtos;

import java.time.LocalDateTime;

public class RolUsuarioDTO {

    private Long idRolUsuario;
    private Long usuarioId;
    private String role;
    private LocalDateTime fechaCreacion;

    public RolUsuarioDTO() {}

    public RolUsuarioDTO(Long usuarioId, Long idUsuario, String role, LocalDateTime fechaCreacion) {
        this.idRolUsuario = idRolUsuario;
        this.usuarioId = usuarioId;
        this.role = role;
        this.fechaCreacion = fechaCreacion;
    }

	public Long getIdRolUsuario() {
		return idRolUsuario;
	}

	public void setIdRolUsuario(Long idRolUsuario) {
		this.idRolUsuario = idRolUsuario;
	}

	
	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
    
    
    

}
