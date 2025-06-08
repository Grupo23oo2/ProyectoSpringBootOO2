package org.example.dtos;

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

}
