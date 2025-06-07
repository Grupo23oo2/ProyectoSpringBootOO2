package dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class RolUsuarioDTO {

    @Getter @Setter
    private Long idRolUsuario;

    @Getter @Setter
    private Long idUsuario;

    @Getter @Setter
    private String role;

    @Getter @Setter
    private LocalDateTime fechaCreacion;

    public RolUsuarioDTO() {}

    public RolUsuarioDTO(Long idRolUsuario, Long idUsuario, String role, LocalDateTime fechaCreacion) {
        this.idRolUsuario = idRolUsuario;
        this.idUsuario = idUsuario;
        this.role = role;
        this.fechaCreacion = fechaCreacion;
    }

	public Long getIdRolUsuario() {
		return idRolUsuario;
	}

	public void setIdRolUsuario(Long idRolUsuario) {
		this.idRolUsuario = idRolUsuario;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
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
