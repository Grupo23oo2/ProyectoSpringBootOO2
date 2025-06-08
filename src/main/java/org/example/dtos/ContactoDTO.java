package org.example.dtos;

import lombok.Getter;
import lombok.Setter;

public class ContactoDTO {

    @Getter @Setter
    private Long idContacto;

    @Getter @Setter
    private String direccion;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String telefono;

    public ContactoDTO() {}

    public ContactoDTO(Long idContacto, String direccion, String email, String telefono) {
        this.idContacto = idContacto;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
    }

}
