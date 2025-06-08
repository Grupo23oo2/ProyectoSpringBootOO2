package org.example.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // o SINGLE_TABLE, dependiendo tu elección
@Getter @Setter
@NoArgsConstructor
public abstract class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long idPersona;
    protected String nombre;
    protected String apellido;
    protected String dni;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    protected Usuario usuario;

    public Persona(Long idPersona, String nombre, String apellido, String dni, Usuario usuario) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.usuario = usuario;
    }

}
