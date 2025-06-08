package org.example.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cliente extends Persona {

    private String cuit;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    @PrimaryKeyJoinColumn // le dice que comparten el mismo id
    private Contacto contacto;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Servicio> servicios = new HashSet<>();

    public Cliente(Long idPersona, String nombre, String apellido, String dni, Usuario usuario, String cuit, Contacto contacto, Set<Servicio> servicios) {
        super(idPersona, nombre, apellido, dni, usuario);
        this.cuit = cuit;
        this.contacto = contacto;
        this.servicios = servicios;
    }

    
}
