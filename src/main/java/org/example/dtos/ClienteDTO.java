package org.example.dtos;

import lombok.Getter;
import lombok.Setter;

public class ClienteDTO {

    @Getter @Setter
    private Long idPersona;

    @Getter @Setter
    private String nombre;

    @Getter @Setter
    private String apellido;

    @Getter @Setter
    private String dni;

    @Getter @Setter
    private Long idUsuario;

    @Getter @Setter
    private String cuit;

    @Getter @Setter
    private Long idContacto;

   public ClienteDTO(){}
    public ClienteDTO(Long idPersona, String nombre, String apellido, String dni, Long idUsuario, String cuit, Long idContacto) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.idUsuario = idUsuario;
        this.cuit = cuit;
        this.idContacto = idContacto;
    }

}
