package org.example.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Lugar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLugar;

    private String direccion;

    @OneToMany(mappedBy = "lugarServicio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Servicio> servicios = new HashSet<>();

    public Lugar(String direccion) {
        this.direccion = direccion;
    }


}
