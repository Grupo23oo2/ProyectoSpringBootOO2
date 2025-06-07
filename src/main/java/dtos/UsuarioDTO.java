package dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class UsuarioDTO {

    @Getter @Setter
    private Long idUsuario;

    @Getter @Setter
    private String nombreUsuario;

    @Getter @Setter
    private String contraseniaUsuario;

    @Getter @Setter
    private boolean estado;

    @Getter @Setter
    private Long idPersona;

    @Getter @Setter
    private List<String> roles;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long idUsuario, String nombreUsuario, String contraseniaUsuario, boolean estado, Long idPersona, List<String> roles) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contraseniaUsuario = contraseniaUsuario;
        this.estado = estado;
        this.idPersona = idPersona;
        this.roles = roles;
    }

    public UsuarioDTO(Long idUsuario, String nombreUsuario, String contraseniaUsuario, boolean estado, Long idPersona) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contraseniaUsuario = contraseniaUsuario;
        this.estado = estado;
        this.idPersona = idPersona;
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

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
    
    
}
