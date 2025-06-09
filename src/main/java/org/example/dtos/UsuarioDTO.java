package org.example.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

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

   /* @Getter @Setter
    private Set<String> roles;*/

    public UsuarioDTO() {
    }

    

    public UsuarioDTO(Long idUsuario, String nombreUsuario, String contraseniaUsuario, boolean estado, Long idPersona) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contraseniaUsuario = contraseniaUsuario;
        this.estado = estado;
        this.idPersona = idPersona;
    }


/*
	public UsuarioDTO(Long idUsuario, String nombreUsuario, String contraseniaUsuario, boolean estado, Long idPersona,
			Set<String> roles) {
		this.idUsuario = idUsuario;
		this.nombreUsuario = nombreUsuario;
		this.contraseniaUsuario = contraseniaUsuario;
		this.estado = estado;
		this.idPersona = idPersona;
		this.roles = roles;
	}*/
}
