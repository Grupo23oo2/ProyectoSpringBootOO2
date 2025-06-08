package org.example.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Contacto {

    @Id
    private Long idContacto; // Es igual al idPersona del Cliente
    private String direccion;
    private String email; // suponiendo que "main" era "mail"
    private String telefono;

    public Contacto(String direccion, String email, String telefono) {
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
    }

    
}
