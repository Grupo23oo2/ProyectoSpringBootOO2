package org.example.turnos.dtos;

import java.time.LocalDateTime;

public class UsuarioDTO {
	
    private Long idUsuario;
    private String nombreUsuario;
    private String contraseniaUsuario;
    private boolean estado;
    private Long idPersona;
    private String email;
	private String rol;
    private LocalDateTime fechaCreacion;

    public UsuarioDTO() {
    }

	public UsuarioDTO(Long idUsuario, String nombreUsuario, String contraseniaUsuario, boolean estado, Long idPersona, 
			String email, String rol, LocalDateTime fechaCreacion) {
		super();
		this.idUsuario = idUsuario;
		this.nombreUsuario = nombreUsuario;
		this.contraseniaUsuario = contraseniaUsuario;
		this.estado = estado;
		this.idPersona = idPersona;
		this.email = email;
		this.rol = rol;
		this.fechaCreacion = fechaCreacion;
	}
    
	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContraseniaUsuario() {
		return contraseniaUsuario;
	}

	public void setContraseniaUsuario(String contraseniaUsuario) {
		this.contraseniaUsuario = contraseniaUsuario;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	} 

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
}
