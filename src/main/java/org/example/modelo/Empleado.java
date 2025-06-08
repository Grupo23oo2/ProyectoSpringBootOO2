package org.example.modelo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Empleado extends Persona {

    private LocalDate fechaInicio;

    @OneToMany(mappedBy = "empleado", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Servicio> servicios = new HashSet<>();

    public Empleado(Long idPersona, String nombre, String apellido, String dni, Usuario usuario, LocalDate fechaInicio, Set<Servicio> servicios) {
        super(idPersona, nombre, apellido, dni, usuario);
        this.fechaInicio = fechaInicio;
        this.servicios = servicios;
    }

}
